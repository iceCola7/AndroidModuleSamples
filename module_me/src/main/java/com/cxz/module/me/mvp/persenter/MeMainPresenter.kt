package com.cxz.module.news.mvp.persenter

import com.cxz.kotlin.baselibs.mvp.BasePresenter
import com.cxz.module.me.mvp.contract.MeMainContract
import com.cxz.module.me.mvp.model.MeMainModel

/**
 * @author chenxz
 * @date 2018/12/25
 * @desc
 */
class MeMainPresenter : BasePresenter<MeMainContract.Model, MeMainContract.View>(), MeMainContract.Presenter {

    override fun createModel(): MeMainContract.Model? = MeMainModel()

}