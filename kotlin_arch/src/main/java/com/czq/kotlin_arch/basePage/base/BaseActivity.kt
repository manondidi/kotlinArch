package com.czq.kotlin_arch.basePage.base

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.czq.kotlin_arch.component.cover.CoverFrameLayout
import com.hjq.toast.ToastUtils
import com.hwangjr.rxbus.RxBus
import com.orhanobut.logger.Logger
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import kotlinx.android.synthetic.main.activity_base.*

abstract class BaseActivity<T : IBasePrensenter> : AppCompatActivity(), IBaseView {

    open lateinit var mPresenter: T

    override fun getContext(): Context {
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!needTitle()) {
            supportActionBar?.hide()
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        initContentView()
        initView()
        mPresenter = createPresenter()
        lifecycle.addObserver(mPresenter)
        RxBus.get().register(mPresenter)
        mPresenter.start()
    }





    override fun autoDispose(): AndroidLifecycleScopeProvider {
        return AndroidLifecycleScopeProvider.from(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    open fun needTitle(): Boolean {
        return true
    }

    abstract fun createPresenter(): T

    abstract fun getLayoutId(): Int

    open fun initContentView() {
        setContentView(getLayoutId())
    }

    open fun initView() {
        title = "BaseActivity"
        coverLayout?.doReload = {
            mPresenter?.start()
        }

    }


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
        Logger.e(it, it?.message ?: "")
        ToastUtils.show("${it?.message ?: "加载失败"}")
    }


    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(mPresenter)
        RxBus.get().unregister(mPresenter)
    }
}
