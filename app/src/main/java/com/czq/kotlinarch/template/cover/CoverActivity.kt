package com.czq.kotlinarch.template.cover

import com.czq.kotlin_arch.basePage.base.BaseActivity
import com.czq.kotlinarch.R
import kotlinx.android.synthetic.main.activity_cover.*

class CoverActivity : BaseActivity<CoverContact.CoverPrensenter>(), CoverContact.CoverView {

    override fun createPresenter(): CoverContact.CoverPrensenter {
        return CoverPresenter(this)
    }

    override fun initView() {
        super.initView()

        btnContent.setOnClickListener {
            showContent()
        }
        btnEmpty.setOnClickListener {
            showEmpty()
        }
        btnError.setOnClickListener {
            showError()
        }
        btnLoading.setOnClickListener {
            showLoading()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_cover
    }
}
