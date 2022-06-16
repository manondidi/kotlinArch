package com.czq.kotlinarch.template.cover

import com.czq.kotlin_arch.basePage.base.BaseFragment
import com.czq.kotlinarch.R
import kotlinx.android.synthetic.main.fragment_cover.*

class CoverFragment : BaseFragment<CoverContact.CoverPrensenter>(), CoverContact.CoverView {


    companion object {
        fun newInstance():CoverFragment{
            return CoverFragment()
        }
    }

    override fun createPresenter(): CoverContact.CoverPrensenter {
        return CoverPresenter(this)
    }

    override fun initView() {
        super.initView()

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_cover
    }
}
