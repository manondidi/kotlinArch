package com.czq.kotlin_arch.basePage.base

import android.content.Context
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

interface IBaseView {

    fun showContent()
    fun showLoading()
    fun showEmpty()
    fun showError()
    fun getContext(): Context
    fun autoDispose(): AndroidLifecycleScopeProvider

}