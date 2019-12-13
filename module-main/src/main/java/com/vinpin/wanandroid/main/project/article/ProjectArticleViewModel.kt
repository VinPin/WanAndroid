package com.vinpin.wanandroid.main.project.article

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vinpin.common.NotifyItem
import com.vinpin.common.net.Resource
import com.vinpin.common.vo.Article
import com.vinpin.wanandroid.main.home.HomeRepository
import com.vinpin.wanandroid.main.project.ProjectRespository
import kotlinx.coroutines.launch

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/12/13 10:55
 *     desc  : 项目的文章的ViewModel
 * </pre>
 */
class ProjectArticleViewModel(application: Application) : AndroidViewModel(application) {

    private var page: Int = 1
    private val mProjectRespository: ProjectRespository = ProjectRespository()
    private val mHomeRepository: HomeRepository = HomeRepository()

    private val mInfos: ArrayList<Article> = ArrayList()

    private val _articleList = MutableLiveData<Resource<List<Article>>>()

    val articleList: LiveData<Resource<List<Article>>> = _articleList

    private val _notifyItem = MutableLiveData<NotifyItem>()

    val notifyItem: LiveData<NotifyItem> = _notifyItem

    override fun onCleared() {
        super.onCleared()
        mInfos.clear()
    }

    fun getArticleList(id: Int) {
        viewModelScope.launch {
            page = 1
            val apiResponse = mProjectRespository.getProjectArticleList(id, page)
            // 列表数据
            mInfos.clear()
            apiResponse.getOrNull()?.let {
                mInfos.addAll(it.datas)
                _articleList.value = Resource.success(mInfos)
            }
            apiResponse.exceptionOrNull()?.let {
                _articleList.value = Resource.failure(it)
            }
        }
    }

    fun getArticleListMore(id: Int) {
        viewModelScope.launch {
            page += 1
            val apiResponse = mProjectRespository.getProjectArticleList(id, page)
            apiResponse.getOrNull()?.let {
                mInfos.addAll(it.datas)
                _articleList.value = Resource.success(mInfos)
            }
            apiResponse.exceptionOrNull()?.let {
                _articleList.value = Resource.failure(it)
            }
        }
    }

    fun collectClicked(item: Article, position: Int) {
        val collect = item.collect
        item.collect = !collect
        _notifyItem.value = NotifyItem.change(position)
        if (collect) {
            uncollect(item, position)
        } else {
            collect(item, position)
        }
    }

    private fun collect(item: Article, position: Int) {
        viewModelScope.launch {
            val apiResponse = mHomeRepository.collect(item.id)
            apiResponse.exceptionOrNull()?.let {
                item.collect = false
                _notifyItem.value = NotifyItem.change(position)
            }
        }
    }

    private fun uncollect(item: Article, position: Int) {
        viewModelScope.launch {
            val apiResponse = mHomeRepository.uncollect(item.id)
            apiResponse.exceptionOrNull()?.let {
                item.collect = true
                _notifyItem.value = NotifyItem.change(position)
            }
        }
    }
}
