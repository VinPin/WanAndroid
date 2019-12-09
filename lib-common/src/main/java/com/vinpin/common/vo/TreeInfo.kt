package com.vinpin.common.vo

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/12/7 11:29
 *     desc  : 知识体系
 * </pre>
 */
data class TreeInfo(
    var courseId: Int = 0,
    var id: Int = 0,
    var name: String? = null,
    var order: Int = 0,
    var parentChapterId: Int = 0,
    var userControlSetTop: Boolean = false,
    var visible: Int = 0,
    var children: List<TreeInfo>? = null
) {

    var parent: Boolean = false
}