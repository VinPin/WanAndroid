package com.vinpin.common.vo

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/12/7 11:29
 *     desc  :
 * </pre>
 */
data class ChapterInfo(
    var courseId: Int = 0,
    var id: Int = 0,
    var name: String? = null,
    var order: Int = 0,
    var parentChapterId: Int = 0,
    var userControlSetTop: Boolean = false,
    var visible: Int = 0,
    var children: List<ChapterInfo>? = null
) {

    var parent: Boolean = false
}