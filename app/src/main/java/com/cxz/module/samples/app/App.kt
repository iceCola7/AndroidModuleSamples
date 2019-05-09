package com.cxz.module.samples.app

import android.content.Context
import android.support.multidex.MultiDex
import com.cxz.kotlin.baselibs.app.BaseApp

/**
 * @author chenxz
 * @date 2018/12/22
 * @desc
 */
class App : BaseApp() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}