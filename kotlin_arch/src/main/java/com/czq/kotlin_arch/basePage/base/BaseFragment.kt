package com.czq.kotlin_arch.basePage.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.czq.kotlin_arch.component.cover.CoverFrameLayout
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_base.*

abstract class BaseFragment<T : IBasePrensenter> : Fragment(), IBaseView {


    open lateinit var mPresenter: T

    override fun getContext(): Context {
        return activity!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        mPresenter = createPresenter()
        lifecycle.addObserver(mPresenter)
        mPresenter.start()
    }

    open fun initView() {
        coverLayout?.coverFrameListener = object : CoverFrameLayout.CoverFrameListener {
            override fun onReload() {
                mPresenter?.start()
            }
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
        Toasty.error(context, "${it?.message ?: "加载失败"}", Toast.LENGTH_SHORT, true).show()
    }


    override fun autoDispose(): AndroidLifecycleScopeProvider {
        return AndroidLifecycleScopeProvider.from(this)
    }

}