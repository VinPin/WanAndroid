package com.vinpin.common

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.vinpin.common.util.ToastUtils


/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/12/4 9:46
 *     desc  : 扩展函数
 * </pre>
 */

fun String?.shortToast(context: Context) {
    this?.let {
        ToastUtils.shortToast(context, it)
    }
}

fun String?.longToast(context: Context) {
    this?.let {
        ToastUtils.longToast(context, it)
    }
}

fun TabLayout.setupWithViewPager2(viewPager: ViewPager2, labels: List<String>) {
    if (labels.size != viewPager.adapter?.itemCount) {
        throw Exception("The size of list and the tab count should be equal!")
    }
    TabLayoutMediator(this, viewPager,
        TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = labels[position]
        }).attach()
}

fun TabLayout.setIndicatorWidth(margin: Int) {
    this.post {
        try {
            // 拿到tabLayout的slidingTabIndicator属性
            val slidingTabIndicatorField =
                this.javaClass.getDeclaredField("slidingTabIndicator")
            slidingTabIndicatorField.isAccessible = true
            val mTabStrip = slidingTabIndicatorField.get(this) as LinearLayout
            for (i in 0 until mTabStrip.childCount) {
                val tabView = mTabStrip.getChildAt(i)
                //拿到tabView的mTextView属性
                val textViewField = tabView.javaClass.getDeclaredField("textView")
                textViewField.isAccessible = true
                val mTextView = textViewField.get(tabView) as TextView
                tabView.setPadding(0, 0, 0, 0)
                // 因为想要的效果是字多宽线就多宽，所以测量mTextView的宽度
                var width = mTextView.width
                if (width == 0) {
                    mTextView.measure(0, 0)
                    width = mTextView.measuredWidth
                }
                // 设置tab左右间距,注意这里不能使用Padding,因为源码中线的宽度是根据tabView的宽度来设置的
                val params = tabView.layoutParams as LinearLayout.LayoutParams
                params.width = width
                params.leftMargin = margin
                params.rightMargin = margin
                tabView.layoutParams = params
                tabView.invalidate()
            }
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }
}