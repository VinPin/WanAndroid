package com.vinpin.common.vo

import android.text.TextUtils

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/11/27 14:21
 *     desc  : 首页文章列表
 * </pre>
 */
data class ArticleList(
    val curPage: Int,
    val datas: List<Article>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
) {

    /**
     * curPage : 2
     * datas : []
     * offset : 20
     * over : false
     * pageCount : 323
     * size : 20
     * total : 6456
     */
}

data class Article(
    val apkLink: String,
    val audit: Int,
    val author: String,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
    val courseId: Int,
    val desc: String,
    val envelopePic: String,
    val fresh: Boolean,
    val id: Int,
    val link: String,
    val niceDate: String,
    val niceShareDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val selfVisible: Int,
    val shareDate: Long,
    val shareUser: String,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: List<Tag>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
) {

    /**
     * apkLink :
     * author : 玉刚说
     * chapterId : 410
     * chapterName : 玉刚说
     * collect : false
     * courseId : 13
     * desc :
     * envelopePic :
     * fresh : false
     * id : 8367
     * link : https://mp.weixin.qq.com/s/uI7Fej1_qSJOJnzQ6offpw
     * niceDate : 2019-05-06
     * origin :
     * prefix :
     * projectLink :
     * publishTime : 1557072000000
     * superChapterId : 408
     * superChapterName : 公众号
     * tags : [{"name":"公众号","url":"/wxarticle/list/410/1"}]
     * title : 深扒 EventBus：register
     * type : 0
     * userId : -1
     * visible : 1
     * zan : 0
     */

    var top: Boolean = false

    fun getDisplayAuthor(): String {
        if (!TextUtils.isEmpty(author)) {
            return author
        }
        if (!TextUtils.isEmpty(shareUser)) {
            return shareUser
        }
        return "匿名"
    }

    fun formatChapterName(): String {
        val names = arrayOf(superChapterName, chapterName)
        val format = StringBuilder()
        for (name in names) {
            if (!TextUtils.isEmpty(name)) {
                if (format.isNotEmpty()) {
                    format.append("·")
                }
                format.append(name)
            }
        }
        return format.toString()
    }
}

data class Tag(
    val name: String,
    val url: String
) {
    /**
     * name : 公众号
     * url : /wxarticle/list/410/1
     */
}

