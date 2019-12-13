package com.vinpin.wanandroid.main.project

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.vinpin.common.RouterConstants
import com.vinpin.common.base.BaseFragment
import com.vinpin.common.setupWithViewPager2
import com.vinpin.common.vo.ChapterInfo
import com.vinpin.wanandroid.main.R
import kotlinx.android.synthetic.main.fragment_project.*

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/11/24 11:09
 *     desc  : 项目
 * </pre>
 */
@Route(path = RouterConstants.MAIN_PROJECTFRAGMENT)
class ProjectFragment : BaseFragment() {

    private val mViewModel: ProjectViewModel by lazy {
        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(mActivity.application)
        ViewModelProviders.of(this, factory).get(ProjectViewModel::class.java)
    }

    override val layoutId: Int get() = R.layout.fragment_project

    override fun getData() {
        mViewModel.chapterList.observe(this, Observer { resource ->
            resource.getOrNull()?.let {
                initViewPager(it)
            }
        })
        mViewModel.getProjectChapters()
    }

    override fun getLazyData() {

    }

    private fun initViewPager(infos: List<ChapterInfo>) {
        val labels: List<String> = ArrayList<String>().apply {
            infos.forEach {
                add(it.name ?: "")
            }
        }
        viewPager.offscreenPageLimit = 1
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = infos.size

            override fun createFragment(position: Int): Fragment {
                val chapterInfo = infos[position]
                return ARouter.getInstance().build(RouterConstants.MAIN_ProjectArticleFragment)
                    .withInt("chapterId", chapterInfo.id).navigation() as BaseFragment
            }
        }
        tabLayout.setupWithViewPager2(viewPager, labels)
    }
}