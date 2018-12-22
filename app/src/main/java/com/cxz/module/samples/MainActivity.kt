package com.cxz.module.samples

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = "/app/main")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
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

    }
}
