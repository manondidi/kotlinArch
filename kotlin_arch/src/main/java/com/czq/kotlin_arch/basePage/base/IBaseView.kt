package com.czq.kotlin_arch.basePage.base

import android.content.Context

interface IBaseView {

    fun showContent()
    fun showLoading()
    fun showEmpty()
    fun showError()

    fun getContext():Context
}