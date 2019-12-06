package com.vinpin.common.vo

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/12/5 15:54
 *     desc  : 首页banner
 * </pre>
 */
data class BannerInfo(
    var desc: String?,
    var id: Int = 0,
    var imagePath: String?,
    var isVisible: Int = 0,
    var order: Int = 0,
    var title: String?,
    var type: Int = 0,
    var url: String?
)