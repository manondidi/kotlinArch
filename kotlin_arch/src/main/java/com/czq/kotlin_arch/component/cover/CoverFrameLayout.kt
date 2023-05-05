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
    var doReload: (() -> Unit)? = null
    var coverListener: CoverListener? = null

    private val inflater by lazy { LayoutInflater.from(context) }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    fun showLoading() {
        if (loadingView == null) {
            loadingView = inflater.inflate(CoverFrameViewConfig.defaultLoadingViewLayout, null)
        }
        if (loadingView?.parent == null) {
            val lp = loadingView?.layoutParams
            this.addView(loadingView)
        }
        loadingView?.visibility = View.VISIBLE
        emptyView?.visibility = View.GONE
        errorView?.visibility = View.GONE
        coverListener?.onShowLoading(loadingView)

    }

    fun showEmpty() {
        if (emptyView == null) {
            emptyView = inflater.inflate(CoverFrameViewConfig.defaultEmptyViewLayout, null)
            emptyView?.setOnClickListener {
                reload()
            }
        }
        if (emptyView?.parent == null) {
            this.addView(emptyView)
        }

        emptyView?.visibility = View.VISIBLE
        loadingView?.visibility = View.GONE
        errorView?.visibility = View.GONE
        coverListener?.onShowEmpty(emptyView)
    }

    fun showError() {
        if (errorView == null) {
            errorView = inflater.inflate(CoverFrameViewConfig.defaultErrorViewLayout, null)
            errorView?.btnRetry?.setOnClickListener {
                reload()
            }
        }
        if (errorView?.parent == null) {
            val lp = errorView?.layoutParams
            this.addView(errorView)
        }
        errorView?.visibility = View.VISIBLE
        loadingView?.visibility = View.GONE
        emptyView?.visibility = View.GONE
        coverListener?.onShowError(errorView)
    }

    fun showContent() {
        errorView?.visibility = View.GONE
        loadingView?.visibility = View.GONE
        emptyView?.visibility = View.GONE
        coverListener?.onShowContent()
    }

    fun reload() {
        if (loadingView?.visibility == View.VISIBLE){
            return
        }
        doReload?.invoke()
    }
}