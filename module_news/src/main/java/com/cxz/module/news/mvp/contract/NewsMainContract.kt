package com.cxz.module.news.mvp.contract

import com.cxz.kotlin.baselibs.mvp.IModel
import com.cxz.kotlin.baselibs.mvp.IPresenter
import com.cxz.kotlin.baselibs.mvp.IView

/**
 * @author chenxz
 * @date 2018/12/25
 * @desc
 */
interface NewsMainContract {

    interface View : IView {

    }

    interface Presenter : IPresenter<View> {

    }

    interface Model : IModel {

    }

}