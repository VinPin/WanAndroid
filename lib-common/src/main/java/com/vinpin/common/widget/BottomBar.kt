package com.vinpin.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.vinpin.common.R
import com.vinpin.commonutils.ResourcesUtils

/**
 * <pre>
 *     author: vinpin
 *     time  : 2019/11/23 14:01
 *     desc  : 底部导航栏
 * </pre>
 */
class BottomBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleRes) {

    private val mTexts = ArrayList<String>(5)
    private val mIconResIds = ArrayList<Int>(5)
    private var mListener: ((view: View, position: Int) -> Unit)? = null

    init {
        this.orientation = HORIZONTAL
        setItemsInternal()
        setCurrentItem(0)
    }

    private fun setItemsInternal() {
        this.weightSum = mTexts.size.toFloat()
        this.removeAllViews()
        for (i in mTexts.indices) {
            val item = createItem()
            item.mText.text = mTexts[i]
            item.mText.textSize = 10f
            item.mIcon.setImageResource(mIconResIds[i])
            item.setOnClickListener {
                setCurrentItem(i)
                mListener?.invoke(it, i)
            }
            this.addView(item)
        }
    }

    private fun createItem(): BottomItem {
        val item = BottomItem(context)
        item.layoutParams = LayoutParams(0, LayoutParams.MATCH_PARENT, 1f)
        return item
    }

    fun setItems(texts: List<String>, iconResIds: List<Int>) {
        mTexts.clear()
        mTexts.addAll(texts)
        mIconResIds.clear()
        mIconResIds.addAll(iconResIds)
        setItemsInternal()
    }

    fun setOnItemClickListener(block: (view: View, position: Int) -> Unit) {
        mListener = block
    }

    fun setCurrentItem(position: Int) {
        for (i in 0 until childCount) {
            val view = getChildAt(i) as? BottomItem
            val selected = i == position
            view?.mIcon?.setColorFilter(
                ResourcesUtils.getColor(
                    if (selected) R.color.bottomBarItemSelected else R.color.bottomBarItem
                )
            )
            view?.mText?.setTextColor(
                ResourcesUtils.getColor(
                    if (selected) R.color.bottomBarItemSelected else R.color.bottomBarItem
                )
            )
        }
    }

    class BottomItem @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleRes: Int = 0
    ) : LinearLayout(context, attrs, defStyleRes) {

        val mIcon = AppCompatImageView(context)
        val mText = TextView(context)

        init {
            this.orientation = VERTICAL
            this.gravity = Gravity.CENTER
            mIcon.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            mText.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            this.addView(mIcon)
            this.addView(mText)
        }
    }
}