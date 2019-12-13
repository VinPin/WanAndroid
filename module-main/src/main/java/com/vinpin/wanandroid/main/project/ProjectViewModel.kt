package com.vinpin.wanandroid.main.project

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vinpin.common.net.Resource
import com.vinpin.common.vo.ChapterInfo
import kotlinx.coroutines.launch

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/12/13 10:55
 *     desc  : 项目Fragment的ViewModel
 * </pre>
 */
class ProjectViewModel(application: Application) : AndroidViewModel(application) {

    private val mProjectRespository: ProjectRespository = ProjectRespository()

    private val mInfos: ArrayList<ChapterInfo> = ArrayList()

    private val _chapterList: MutableLiveData<Resource<List<ChapterInfo>>> = MutableLiveData()

    val chapterList: LiveData<Resource<List<ChapterInfo>>> = _chapterList

    override fun onCleared() {
        super.onCleared()
        mInfos.clear()
    }

    fun getProjectChapters() {
        viewModelScope.launch {
            val apiResponse = mProjectRespository.getProjectChapters()
            mInfos.clear()
            apiResponse.getOrNull()?.let {
                mInfos.addAll(it)
                _chapterList.value = Resource.success(mInfos)
            }
            apiResponse.exceptionOrNull()?.let {
                _chapterList.value = Resource.failure(it)
            }
        }
    }
}