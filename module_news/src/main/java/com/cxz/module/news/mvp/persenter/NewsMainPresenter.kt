package com.cxz.module.news.mvp.persenter

import com.cxz.kotlin.baselibs.mvp.BasePresenter
import com.cxz.module.news.mvp.contract.NewsMainContract
import com.cxz.module.news.mvp.model.NewsMainModel

/**
 * @author chenxz
 * @date 2018/12/25
 * @desc
 */
class NewsMainPresenter : BasePresenter<NewsMainContract.Model, NewsMainContract.View>(), NewsMainContract.Presenter {

    override fun createModel(): NewsMainContract.Model? = NewsMainModel()

}