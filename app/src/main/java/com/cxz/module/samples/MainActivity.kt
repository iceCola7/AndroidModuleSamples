package com.cxz.module.samples

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.cxz.kotlin.baselibs.ext.showToast
import com.cxz.module.news.NewsService
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = "/app/main")
class MainActivity : AppCompatActivity() {

    @Autowired(name = "/news/service")
    @JvmField
    var newsService: NewsService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_news.setOnClickListener {
            ARouter.getInstance().build("/news/main").navigation()
        }
        btn_video.setOnClickListener {
            ARouter.getInstance().build("/video/main").navigation()
        }
        btn_me.setOnClickListener {
            ARouter.getInstance().build("/me/main").navigation()
        }
        btn_news_service.setOnClickListener {
            showToast(newsService?.getNewsName().toString())
        }

    }
}
