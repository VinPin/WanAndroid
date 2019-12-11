package com.vinpin.wanandroid.main.home

import android.content.Context
import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.vinpin.adapter.CommonAdapter
import com.vinpin.adapter.base.ViewHolder
import com.vinpin.common.vo.Article
import com.vinpin.commonutils.ResourcesUtils
import com.vinpin.commonutils.SizeUtils
import com.vinpin.imageloader.ImageLoader
import com.vinpin.selectorhelper.SelectorHelper
import com.vinpin.selectorhelper.ShapeHelper
import com.vinpin.wanandroid.main.R

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/11/29 10:51
 *     desc  : 文章列表适配器
 * </pre>
 */
class ArticleAdapter(
    val context: Context,
    datas: List<Article>
) : CommonAdapter<Article>(context, R.layout.item_article_list, datas) {

    private var mCollectListener: ((view: View, item: Article, position: Int) -> Unit)? = null

    override fun convert(holder: ViewHolder, info: Article, position: Int) {
        holder.getView<View>(R.id.rl_content).background =
            SelectorHelper.drawableSelector()
                .pressed(
                    true, ShapeHelper.getInstance()
                        .solidColor(R.color.articleItemPressed)
                        .cornerRadius(SizeUtils.dp2px(5f).toFloat())
                        .create()
                )
                .defaultDrawable(
                    ShapeHelper.getInstance()
                        .solidColor(R.color.articleItem)
                        .cornerRadius(SizeUtils.dp2px(5f).toFloat())
                        .create()
                ).create()
        holder.setText(R.id.txt_author, info.getDisplayAuthor())
        holder.setText(R.id.txt_time, info.niceDate)
        holder.setText(R.id.txt_title, Html.fromHtml(info.title).toString())

        if (!TextUtils.isEmpty(info.desc)) {
            holder.getView<TextView>(R.id.txt_title).setSingleLine(true)
            holder.setText(R.id.txt_desc, Html.fromHtml(info.desc).toString())
            holder.setVisible(R.id.txt_desc, true)
        } else {
            holder.getView<TextView>(R.id.txt_title).setSingleLine(false)
            holder.setText(R.id.txt_desc, null)
            holder.setVisible(R.id.txt_desc, false)
        }

        if (info.top) {
            holder.getView<TextView>(R.id.txt_top).background = ShapeHelper.getInstance()
                .stroke(SizeUtils.dp2px(1f), R.color.text_orange)
                .cornerRadius(SizeUtils.dp2px(1.5f).toFloat())
                .create()
        }
        holder.setVisible(R.id.txt_top, info.top)

        if (info.fresh) {
            holder.getView<TextView>(R.id.txt_fresh).background = ShapeHelper.getInstance()
                .stroke(SizeUtils.dp2px(1f), R.color.text_orange)
                .cornerRadius(SizeUtils.dp2px(1.5f).toFloat())
                .create()
        }
        holder.setVisible(R.id.txt_fresh, info.fresh)

        if (info.tags?.isNotEmpty() == true) {
            holder.getView<TextView>(R.id.txt_tag).background = ShapeHelper.getInstance()
                .stroke(SizeUtils.dp2px(1f), R.color.text_green)
                .cornerRadius(SizeUtils.dp2px(1.5f).toFloat())
                .create()
            holder.setText(R.id.txt_tag, info.tags!![0].name)
            holder.setVisible(R.id.txt_tag, true)
        } else {
            holder.setVisible(R.id.txt_tag, false)
        }

        if (info.collect) {
            holder.getView<ImageView>(R.id.img_collect)
                .setColorFilter(ResourcesUtils.getColor(R.color.bottomBarItemSelected))
        } else {
            holder.getView<ImageView>(R.id.img_collect)
                .setColorFilter(ResourcesUtils.getColor(R.color.bottomBarItem))
        }
        holder.setOnClickListener(R.id.img_collect) {
            mCollectListener?.invoke(it, info, position)
        }

        holder.setText(
            R.id.txt_chapter_name,
            Html.fromHtml(info.formatChapterName()).toString()
        )

        val imgCover = holder.getView<ImageView>(R.id.img_cover)
        if (info.envelopePic?.isNotEmpty() == true) {
            ImageLoader.with(context).url(info.envelopePic)
                .placeholder(R.drawable.image_holder)
                .error(R.drawable.image_holder)
                .into(imgCover)
            imgCover.visibility = View.VISIBLE
        } else {
            imgCover.visibility = View.GONE
        }
    }

    fun setOnCollectClickListener(block: (view: View, item: Article, position: Int) -> Unit) {
        mCollectListener = block
    }
}
