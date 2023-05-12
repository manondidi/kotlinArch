package com.czq.kotlin_arch.basePage.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.czq.kotlin_arch.component.cover.CoverFrameLayout
import com.hjq.toast.ToastUtils
import com.hwangjr.rxbus.RxBus
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import kotlinx.android.synthetic.main.fragment_base.*

abstract class BaseFragment<T : IBasePrensenter> : Fragment(), IBaseView {


    open lateinit var mPresenter: T

    override fun getContext(): Context {
        return activity!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        mPresenter = createPresenter()
        RxBus.get().register(mPresenter)
        lifecycle.addObserver(mPresenter)
        mPresenter.start()
    }

    override fun onDestroyView() {
        lifecycle.removeObserver(mPresenter)
        RxBus.get().unregister(mPresenter)
        super.onDestroyView()

    }

    open fun initView() {
        coverLayout?.doReload = {
            showLoading()
            mPresenter?.start()
        }
    }


    abstract fun createPresenter(): T

    abstract fun getLayoutId(): Int


    override fun showContent() {
        coverLayout?.showContent()
    }

    override fun showLoading() {
        coverLayout?.showLoading()
    }

    override fun showEmpty() {
        coverLayout?.showEmpty()
    }

    override fun showError(it: Throwable?) {
        coverLayout?.showError()
        ToastUtils.show("${it?.message ?: "加载失败"}")
    }


    override fun autoDispose(): AndroidLifecycleScopeProvider {
        return AndroidLifecycleScopeProvider.from(this)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}