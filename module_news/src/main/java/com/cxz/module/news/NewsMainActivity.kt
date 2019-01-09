package com.cxz.module.news

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.cxz.kotlin.baselibs.base.BaseMvpActivity
import com.cxz.kotlin.baselibs.ext.showToast
import com.cxz.module.news.mvp.contract.NewsMainContract
import com.cxz.module.news.mvp.persenter.NewsMainPresenter

@Route(path = "/news/main")
class NewsMainActivity : BaseMvpActivity<NewsMainContract.View, NewsMainContract.Presenter>(), NewsMainContract.View {

    @Autowired
    @JvmField
    var key1: String? = null
    @Autowired
    @JvmField
    var key2: String? = null

    override fun initView() {
        ARouter.getInstance().inject(this)
        super.initView()
        showToast("key1: $key1, key2: $key2")
    }

    override fun start() {
    }

    override fun createPresenter(): NewsMainContract.Presenter = NewsMainPresenter()

    override fun attachLayoutRes(): Int = R.layout.news_activity_news_main

}
