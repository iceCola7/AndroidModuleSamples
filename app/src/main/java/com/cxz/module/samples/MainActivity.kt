package com.cxz.module.samples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.cxz.kotlin.baselibs.ext.showToast
import com.cxz.kotlin.baselibs.provider.NewsService
import kotlinx.android.synthetic.main.app_activity_main.*

@Route(path = "/app/main")
class MainActivity : AppCompatActivity() {

    @Autowired(name = "/news/service")
    @JvmField
    var newsService: NewsService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity_main)

        btn_news.setOnClickListener {
            ARouter.getInstance().build("/news/main") // 目标页面
                .withString("key1", "test_key1")  // 参数
                .withString("key2", "test_key2")  // 参数
                .navigation()
        }
        btn_video.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("key1", "test_key1")
            bundle.putString("key2", "test_key2")
            ARouter.getInstance().build("/video/main")
                .with(bundle)
                .navigation()
        }
        btn_me.setOnClickListener {
            ARouter.getInstance().build("/me/main")
                .navigation()
        }
        btn_news_service.setOnClickListener {
            showToast(newsService?.getNewsName().toString())
        }

    }
}
