package com.vinpin.wanandroid.main.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vinpin.common.NotifyItem
import com.vinpin.common.net.Resource
import com.vinpin.common.vo.Article
import com.vinpin.common.vo.BannerInfo
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/11/27 14:44
 *     desc  : 主页Fragment的ViewModel
 * </pre>
 */
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var page: Int = 0
    private val mHomeRepository: HomeRepository = HomeRepository()

    private val mInfos: ArrayList<Article> = ArrayList()

    private val _articleList = MutableLiveData<Resource<List<Article>>>()

    val articleList: LiveData<Resource<List<Article>>> = _articleList

    private val _bannerList = MutableLiveData<Resource<List<BannerInfo>>>()

    val bannerList: LiveData<Resource<List<BannerInfo>>> = _bannerList

    private val _notifyItem = MutableLiveData<NotifyItem>()

    val notifyItem: LiveData<NotifyItem> = _notifyItem

    fun getArticleList() {
        viewModelScope.launch {
            page = 0
            val deferred = async {
                mHomeRepository.getTopArticleList()
            }
            val deferred1 = async {
                mHomeRepository.getArticleList(page)
            }
            val deferred2 = async {
                mHomeRepository.getBanner()
            }
            val topApiResponse = deferred.await()
            val apiResponse = deferred1.await()
            val bannerApiResponse = deferred2.await()
            // 列表数据
            mInfos.clear()
            topApiResponse.getOrNull()?.let {
                mInfos.addAll(it)
            }
            apiResponse.getOrNull()?.let {
                mInfos.addAll(it.datas)
                _articleList.value = Resource.success(mInfos)
            }
            apiResponse.exceptionOrNull()?.let {
                _articleList.value = Resource.failure(it)
            }
            // banner数据
            bannerApiResponse.getOrNull()?.let {
                _bannerList.value = Resource.success(it)
            }
            bannerApiResponse.exceptionOrNull()?.let {
                _bannerList.value = Resource.failure(it)
            }
        }
    }

    fun getArticleListMore() {
        viewModelScope.launch {
            page += 1
            val apiResponse = mHomeRepository.getArticleList(page)
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