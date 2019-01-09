package com.cxz.module.video

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.cxz.kotlin.baselibs.base.BaseMvpActivity
import com.cxz.kotlin.baselibs.ext.showToast
import com.cxz.module.news.mvp.contract.VideoMainContract
import com.cxz.module.news.mvp.persenter.VideoMainPresenter

@Route(path = "/video/main")
class VideoMainActivity : BaseMvpActivity<VideoMainContract.View, VideoMainContract.Presenter>(), VideoMainContract.View {

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

    override fun createPresenter(): VideoMainContract.Presenter = VideoMainPresenter()

    override fun attachLayoutRes(): Int = R.layout.video_activity_video_main

}
