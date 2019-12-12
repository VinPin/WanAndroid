package com.vinpin.wanandroid.main.knowledge

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setMargins
import com.google.android.flexbox.FlexboxLayout
import com.vinpin.adapter.CommonAdapter
import com.vinpin.adapter.base.ViewHolder
import com.vinpin.common.vo.ChapterInfo
import com.vinpin.commonutils.ResourcesUtils
import com.vinpin.commonutils.SizeUtils
import com.vinpin.selectorhelper.SelectorHelper
import com.vinpin.selectorhelper.ShapeHelper
import com.vinpin.wanandroid.main.R
import java.util.*

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/12/11 14:32
 *     desc  : 知识体系列表适配器
 * </pre>
 */
class KnowledgeAdapter(
    val context: Context,
    datas: List<ChapterInfo>
) : CommonAdapter<ChapterInfo>(context, R.layout.item_konwnledge_list, datas) {

    private val mChildTextViewCaches: Queue<TextView> = LinkedList<TextView>()

    private var mOnChildItemClickListener: ((view: View, item: ChapterInfo, position: Int) -> Unit)? =
        null

    override fun convert(holder: ViewHolder, info: ChapterInfo, position: Int) {
        holder.setText(R.id.txt_parent, info.name)
        val flexboxLayout = holder.getView<FlexboxLayout>(R.id.flexbox_child)
        flexboxLayout.removeAllViews()
        for (i in 0 until (info.children?.size ?: 0)) {
            val textView = createOrGetCacheTextView(flexboxLayout)
            textView.text = info.children!![i].name
            textView.setOnClickListener {
                mOnChildItemClickListener?.invoke(it, info, i)
            }
            flexboxLayout.addView(textView)
        }
        holder.convertView.background = SelectorHelper.drawableSelector()
            .pressed(
                true,
                ShapeHelper.getInstance().solidColor(R.color.articleItemPressed).create()
            )
            .defaultDrawable(
                ShapeHelper.getInstance().solidColor(R.color.articleItem).create()
            ).create()
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        val flexboxLayout = holder.getView<FlexboxLayout>(R.id.flexbox_child)
        for (i in 0 until flexboxLayout.childCount) {
            val textView = flexboxLayout.getChildAt(i) as TextView
            textView.text = null
            mChildTextViewCaches.offer(textView)
        }
        flexboxLayout.removeAllViews()
    }

    private fun createOrGetCacheTextView(flexboxLayout: FlexboxLayout): TextView {
        val view: TextView? = mChildTextViewCaches.poll()
        if (view != null) {
            return view
        }
        return TextView(flexboxLayout.context).apply {
            this.textSize = 13f
            this.setTextColor(ResourcesUtils.getColor(R.color.text_sub))
            this.layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).also {
                it.setMargins(SizeUtils.dp2px(5f))
            }
            this.setPadding(
                SizeUtils.dp2px(10f),
                SizeUtils.dp2px(5f),
                SizeUtils.dp2px(10f),
                SizeUtils.dp2px(5f)
            )
            this.background = ShapeHelper.getInstance()
                .solidColor(R.color.activity)
                .cornerRadius(SizeUtils.dp2px(100f).toFloat())
                .create()
        }
    }

    fun setOnChildItemClickListener(block: (view: View, item: ChapterInfo, position: Int) -> Unit) {
        mOnChildItemClickListener = block
    }
}