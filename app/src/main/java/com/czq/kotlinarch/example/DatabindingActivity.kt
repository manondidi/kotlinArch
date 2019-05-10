package com.czq.kotlinarch.example


import androidx.databinding.DataBindingUtil
import com.czq.kotlin_arch.basePage.base.BaseActivity
import com.czq.kotlinarch.R
import com.czq.kotlinarch.data.viewModel.DataBindingVM


class DatabindingActivity : BaseActivity<DatabindingContract.IPrensenter>(), DatabindingContract.IView {

    var binding: com.czq.kotlinarch.databinding.ActivityDatabindingBinding? = null
    override fun createPresenter(): DatabindingContract.IPrensenter {
        return DatabindingPresenter(this)
    }

    override fun initView() {
        super.initView()
        title = "databindingTest"
    }

    override fun initContentView() {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        val vm = DataBindingVM()
        vm.txt = "databindingTest"
        binding?.vm = vm

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_databinding
    }
}
