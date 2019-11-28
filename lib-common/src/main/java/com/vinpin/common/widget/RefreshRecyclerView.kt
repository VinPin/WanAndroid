package com.vinpin.common.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.SmartRefreshLayout

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

    val recyclerView: RecyclerView = RecyclerView(context)

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
        this.setRefreshContent(recyclerView)
    }

    fun setAdapter(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>) {
        recyclerView.adapter = adapter
    }
}