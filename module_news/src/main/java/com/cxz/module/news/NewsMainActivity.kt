package com.cxz.module.news

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.cxz.kotlin.baselibs.ext.showToast

@Route(path = "/news/main")
class NewsMainActivity : AppCompatActivity() {

    @Autowired
    @JvmField
    var key1: String? = null
    @Autowired
    @JvmField
    var key2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_activity_news_main)

        showToast("key1: $key1, key2: $key2")

    }

}
