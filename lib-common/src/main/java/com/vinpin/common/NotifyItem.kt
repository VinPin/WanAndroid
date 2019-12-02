package com.vinpin.common

import androidx.annotation.NonNull

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/12/2 15:00
 *     desc  : 封装的RecyclerView通知条目操作对象
 * </pre>
 */
class NotifyItem private constructor() {

    var position: Int = 0

    var fromPosition: Int = 0

    var toPosition: Int = 0

    var type: Type = Type.CHANGE

    /**
     * 通知操作的具体类型
     */
    enum class Type {
        /**
         * 插入数据
         */
        INSERT,
        /**
         * 移动数据，交换位置
         */
        MOVE,
        /**
         * 删除数据
         */
        REMOVE,
        /**
         * 改变数据
         */
        CHANGE
    }

    companion object {

        /**
         * 通知插入了一条数据
         *
         * @param position 索引值
         */
        @NonNull
        fun insert(position: Int): NotifyItem {
            val info = NotifyItem()
            info.type = Type.INSERT
            info.position = position
            return info
        }

        /**
         * 通知两条数据换位置
         *
         * @param fromPosition 起始位置索引值
         * @param toPosition   目的位置索引值
         */
        @NonNull
        fun move(fromPosition: Int, toPosition: Int): NotifyItem {
            val info = NotifyItem()
            info.type = Type.MOVE
            info.fromPosition = fromPosition
            info.toPosition = toPosition
            return info
        }

        /**
         * 通知移除了一条数据
         *
         * @param position 索引值
         */
        @NonNull
        fun remove(position: Int): NotifyItem {
            val info = NotifyItem()
            info.type = Type.REMOVE
            info.position = position
            return info
        }

        /**
         * 通知某个数据发生了改变
         *
         * @param position 索引值
         */
        @NonNull
        fun change(position: Int): NotifyItem {
            val info = NotifyItem()
            info.type = Type.CHANGE
            info.position = position
            return info
        }
    }
}
