package com.vinpin.wanandroid.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.vinpin.common.RouterConstants
import com.vinpin.common.base.BaseActivity
import com.vinpin.common.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * <pre>
 *     author: vinpin
 *     time  : 2019/11/22 16:15
 *     desc  : 主界面
 * </pre>
 */
@Route(path = RouterConstants.MAIN_MAINACTIVITY)
class MainActivity : BaseActivity() {

    override val layoutId: Int get() = R.layout.activity_main

    override fun getData(savedInstanceState: Bundle?) {
        val bundle = savedInstanceState ?: intent.extras
        bundle?.let {
            initBottomBar()
            initViewPager()
            val currentItem = it.getInt("currentItem")
            bottomBar.setCurrentItem(currentItem)
            viewPager.setCurrentItem(currentItem, false)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("currentItem", viewPager.currentItem)
        super.onSaveInstanceState(outState)
    }

    private fun initBottomBar() {
        val texts = listOf("首页", "体系", "公众号", "项目", "我的")
        val iconResIds = listOf(
            R.drawable.ic_bottom_bar_home,
            R.drawable.ic_bottom_bar_knowledge,
            R.drawable.ic_bottom_bar_wechat,
            R.drawable.ic_bottom_bar_project,
            R.drawable.ic_bottom_bar_mine
        )
        bottomBar.setItems(texts, iconResIds)
        bottomBar.setOnItemClickListener { _, position ->
            viewPager.setCurrentItem(position, false)
        }
    }

    private fun initViewPager() {
        val fragments = listOf(
            ARouter.getInstance().build(RouterConstants.MAIN_HOMEFRAGMENT).navigation() as BaseFragment,
            ARouter.getInstance().build(RouterConstants.MAIN_KNOWLEDGEFRAGMENT).navigation() as BaseFragment,
            ARouter.getInstance().build(RouterConstants.MIAN_WECHATFRAGMENT).navigation() as BaseFragment,
            ARouter.getInstance().build(RouterConstants.MAIN_PROJECTFRAGMENT).navigation() as BaseFragment,
            ARouter.getInstance().build(RouterConstants.MAIN_MINEFRAGMENT).navigation() as BaseFragment
        )
        viewPager.isUserInputEnabled = false
        viewPager.offscreenPageLimit = 4
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = fragments.size

            override fun createFragment(position: Int): Fragment = fragments[position]
        }
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bottomBar.setCurrentItem(position)
            }
        })
    }
}