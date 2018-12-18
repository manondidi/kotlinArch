package com.czq.kotlinarch.example


import com.czq.kotlin_arch.basePage.base.BaseActivity
import com.czq.kotlinarch.R

class NoCoverActivity : BaseActivity<NoCoverContract.IPrensenter>(), NoCoverContract.IView {

    override fun createPresenter(): NoCoverContract.IPrensenter {
        return NoCoverPresenter(this)
    }

    override fun initView() {
        super.initView()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_no_cover
    }
}
