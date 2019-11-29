package com.vinpin.wanandroid.main.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vinpin.common.net.Resource
import com.vinpin.common.vo.Article
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

    fun getArticleList() {
        viewModelScope.launch {
            page = 0
            val deferred = async {
                mHomeRepository.getTopArticleList()
            }
            val deferred1 = async {
                mHomeRepository.getArticleList(page)
            }
            val topApiResponse = deferred.await()
            val apiResponse = deferred1.await()
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
}