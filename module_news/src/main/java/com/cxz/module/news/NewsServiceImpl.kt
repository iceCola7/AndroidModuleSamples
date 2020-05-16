package com.cxz.module.news

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.cxz.kotlin.baselibs.provider.NewsService

/**
 * @author chenxz
 * @date 2018/12/22
 * @desc
 */
@Route(path = "/news/service", name = "News")
class NewsServiceImpl : NewsService {

    override fun getNewsName(): String = "这是一条新闻信息"

    override fun init(context: Context?) {
    }
}