package com.vinpin.wanandroid.main.knowledge

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vinpin.common.net.Resource
import com.vinpin.common.vo.TreeInfo
import kotlinx.coroutines.launch

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/12/9 9:19
 *     desc  : 知识体系Fragment的ViewModel
 * </pre>
 */
class KnowledgeViewModel(application: Application) : AndroidViewModel(application) {

    private val mKnowledgeRespository: KnowledgeRespository = KnowledgeRespository()

    private val mInfos: ArrayList<TreeInfo> = ArrayList()

    private val _treeList: MutableLiveData<Resource<List<TreeInfo>>> = MutableLiveData()

    val treeList: LiveData<Resource<List<TreeInfo>>> = _treeList

    fun getTreeList() {
        viewModelScope.launch {
            val apiResponse = mKnowledgeRespository.getTreeList()
            mInfos.clear()
            apiResponse.getOrNull()?.let {
                mInfos.addAll(it)
                _treeList.value = Resource.success(mInfos)
            }
            apiResponse.exceptionOrNull()?.let {
                _treeList.value = Resource.failure(it)
            }
        }
    }
}