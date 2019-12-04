package com.vinpin.common.vo

/**
 * <pre>
 *     author: vinpin
 *     time  : 2019/11/20 16:53
 *     desc  : 用户登陆返回的数据对象
 * </pre>
 */
data class UserInfo(
    var admin: Boolean,
    var chapterTops: List<String>?,
    var collectIds: List<Int>?,
    var id: Int,
    var email: String?,
    var icon: String?,
    var nickname: String?,
    var username: String?
)