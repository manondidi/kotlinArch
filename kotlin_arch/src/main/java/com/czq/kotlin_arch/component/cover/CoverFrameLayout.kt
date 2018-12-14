package com.czq.kotlin_arch.component.cover

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.children
import com.czq.kotlin_arch.R
import kotlinx.android.synthetic.main.common_error_view.view.*

class CoverFrameLayout : FrameLayout {

    var loadingView: View? = null
    var emptyView: View? = null
    var errorView: View? = null
    val inflater by lazy { LayoutInflater.from(context) }
    var coverFrameListener: CoverFrameListener? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    interface CoverFrameListener {
        fun onReload()
    }

    fun showLoading() {
        if (loadingView == null) {
            loadingView = inflater.inflate(R.layout.common_loading_view, null)
        }
        if (loadingView?.parent == null) {
            val lp = loadingView?.layoutParams
            this.addView(loadingView)
        }
        children.forEach {
            if (it != loadingView) {
                it.visibility = View.GONE
            } else {
                it.visibility = View.VISIBLE
            }
        }
    }

    fun showEmpty() {
        if (emptyView == null) {
            emptyView = inflater.inflate(R.layout.common_empty_view, null)
            emptyView?.setOnClickListener {
                doReload()
            }
        }
        if (emptyView?.parent == null) {
            val lp = emptyView?.layoutParams
            this.addView(emptyView)
        }
        children.forEach {
            if (it != emptyView) {
                it.visibility = View.GONE
            } else {
                it.visibility = View.VISIBLE
            }
        }
    }

    fun showError() {
        if (errorView == null) {
            errorView = inflater.inflate(R.layout.common_error_view, null)
            errorView?.btnRetry?.setOnClickListener {
                doReload()
            }
        }
        if (errorView?.parent == null) {
            val lp = errorView?.layoutParams
            this.addView(errorView)
        }
        children.forEach {
            if (it != errorView) {
                it.visibility = View.GONE
            } else {
                it.visibility = View.VISIBLE
            }
        }
    }

    fun showContent() {
        children.forEach {
            if (it == emptyView || it == loadingView || it == errorView) {
                it.visibility = View.GONE
            } else {
                it.visibility = View.VISIBLE
            }
        }
    }

    fun doReload() {
        showLoading()
        coverFrameListener?.onReload()
    }
}