package com.vinpin.common.widget

import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.vinpin.common.R

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/11/29 16:02
 *     desc  : 标题栏
 * </pre>
 */
class TitleBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleRes: Int = 0
) : RelativeLayout(context, attrs, defStyleRes) {

    val leftParent: LinearLayout = LinearLayout(context)
    val backIcon: ImageView = ImageView(context)
    val title: TextView = TextView(context)
    val rightParent: LinearLayout = LinearLayout(context)
    val line: View = View(context)

    private var backListener: ((v: View) -> Unit)? = null

    init {
        leftParent.id = ViewCompat.generateViewId()
        val leftParm = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        leftParm.addRule(ALIGN_PARENT_START)
        leftParent.layoutParams = leftParm
        addView(leftParent)

        rightParent.id = ViewCompat.generateViewId()
        val rightParm = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        rightParm.addRule(ALIGN_PARENT_END)
        rightParent.layoutParams = rightParm
        addView(rightParent)

        title.id = ViewCompat.generateViewId()
        val titleParm = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        titleParm.addRule(RIGHT_OF, leftParent.id)
        titleParm.addRule(LEFT_OF, rightParent.id)
        titleParm.addRule(CENTER_VERTICAL)
        title.layoutParams = titleParm
        addView(title)

        val lineParm = LayoutParams(LayoutParams.MATCH_PARENT, dp2px(0.5f))
        lineParm.addRule(ALIGN_PARENT_BOTTOM)
        line.layoutParams = lineParm
        addView(line)

        initIcons()
    }

    private fun initIcons() {
        setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
        line.setBackgroundColor(ContextCompat.getColor(context, R.color.titleBarLine))

        title.setTextColor(ContextCompat.getColor(context, R.color.text_main))
        title.textSize = 18f
        title.typeface = Typeface.DEFAULT_BOLD
        title.maxLines = 1
        title.ellipsize = TextUtils.TruncateAt.END
        (title.layoutParams as LayoutParams).marginStart = dp2px(15f)

        val backParm = LayoutParams(dp2px(40f), LayoutParams.MATCH_PARENT)
        backIcon.layoutParams = backParm
        backIcon.setImageResource(R.drawable.ic_back)
        backIcon.setPadding(dp2px(15f), 0, dp2px(5f), 0)
        backIcon.scaleType = ImageView.ScaleType.FIT_CENTER
        backIcon.adjustViewBounds = true
        backIcon.setColorFilter(R.color.bottomBarItem)
        backIcon.setOnClickListener {
            backListener?.invoke(it)
        }
        leftParent.addView(backIcon)
    }

    fun setOnBackListener(block: (v: View) -> Unit) {
        backListener = block
    }

    private fun dp2px(dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}
