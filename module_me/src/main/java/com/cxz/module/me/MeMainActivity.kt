package com.cxz.module.me

import com.alibaba.android.arouter.facade.annotation.Route
import com.cxz.kotlin.baselibs.base.BaseMvpActivity
import com.cxz.module.me.mvp.contract.MeMainContract
import com.cxz.module.news.mvp.persenter.MeMainPresenter

@Route(path = "/me/main")
class MeMainActivity : BaseMvpActivity<MeMainContract.View, MeMainContract.Presenter>(), MeMainContract.View {

    override fun initView() {
        super.initView()
    }

    override fun start() {
    }

    override fun createPresenter(): MeMainContract.Presenter = MeMainPresenter()

    override fun attachLayoutRes(): Int = R.layout.me_activity_me_main

}
