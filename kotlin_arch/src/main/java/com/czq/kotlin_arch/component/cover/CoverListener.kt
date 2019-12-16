package com.czq.kotlin_arch.component.cover

import android.view.View

interface CoverListener {
    fun onShowContent() {

    }

    fun onShowLoading(loadingView: View?) {

    }

    fun onShowError(errorView: View?) {

    }

    fun onShowEmpty(emptyView: View?) {

    }

}