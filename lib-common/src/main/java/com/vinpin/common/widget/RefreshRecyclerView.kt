package com.vinpin.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.vinpin.adapter.wrapper.HeaderAndFooterWrapper
import com.vinpin.common.util.LogUtils

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/11/27 13:43
 *     desc  : 基于SmartRefreshLayout封装的RecyclerView
 * </pre>
 */
class RefreshRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : SmartRefreshLayout(context, attrs) {

    companion object {
        const val TAG = "RefreshRecyclerView"
    }

    val recyclerView: RecyclerView = RecyclerView(context)

    private var mHeaderAndFooterWrapper: HeaderAndFooterWrapper<out RecyclerView.ViewHolder>? = null

    var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        set(value) {
            recyclerView.layoutManager = value
            field = value
        }

    var itemAnimator: RecyclerView.ItemAnimator = DefaultItemAnimator()
        set(value) {
            recyclerView.itemAnimator = value
            field = value
        }

    init {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = itemAnimator
        recyclerView.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        this.setRefreshContent(recyclerView)
    }

    fun setAdapter(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>) {
        mHeaderAndFooterWrapper = HeaderAndFooterWrapper(adapter)
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                mHeaderAndFooterWrapper?.notifyDataSetChanged()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                mHeaderAndFooterWrapper?.notifyItemRangeChanged(
                    positionStart + getHeadersCount(),
                    itemCount
                )
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                mHeaderAndFooterWrapper?.notifyItemRangeInserted(
                    positionStart + getHeadersCount(),
                    itemCount
                )
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                mHeaderAndFooterWrapper?.notifyItemMoved(
                    fromPosition + getHeadersCount(),
                    toPosition + getHeadersCount()
                )
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                mHeaderAndFooterWrapper?.notifyItemRangeRemoved(
                    positionStart + getHeadersCount(),
                    itemCount
                )
            }
        })
        recyclerView.adapter = mHeaderAndFooterWrapper
    }

    fun getAdapter() = recyclerView.adapter

    /**
     * 添加头布局，务必执行在setAdapter之后
     */
    fun addHeaderView(headerView: View) {
        mHeaderAndFooterWrapper?.addHeaderView(headerView) ?: LogUtils.e(TAG,
            "添加头布局，务必执行在setAdapter之后"
        )
    }

    /**
     * 添加脚布局，务必执行在setAdapter之后
     */
    fun addFootView(footView: View) {
        mHeaderAndFooterWrapper?.addFootView(footView) ?: LogUtils.e(TAG,
            "添加脚布局，务必执行在setAdapter之后")
    }

    fun getHeadersCount(): Int = mHeaderAndFooterWrapper?.headersCount ?: 0

    fun getFootersCount(): Int = mHeaderAndFooterWrapper?.footersCount ?: 0

}