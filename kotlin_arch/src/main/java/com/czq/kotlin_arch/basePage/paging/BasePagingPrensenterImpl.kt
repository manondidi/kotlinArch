package com.czq.kotlin_arch.basePage.base

import android.widget.Toast
import com.czq.kotlin_arch.paging.PagingStrategy
import es.dmoral.toasty.Toasty

abstract class BasePagingPrensenterImpl(val mView: IBasePagingView) : IBasePrensenter {


    val datasource = arrayListOf<Any>()
    val pagingList = arrayListOf<Any>()

    var mPagingStrategy: PagingStrategy? = null
        get

    override fun start() {
        mPagingStrategy = getPagingStrategy()
        mView.beginRefresh()
    }

    abstract fun getPagingStrategy(): PagingStrategy?

    abstract fun onLoadData(pagingStrategy: PagingStrategy?)

    fun resetPage() {
        mPagingStrategy?.resetPage()
    }

    fun loadData() {
        onLoadData(mPagingStrategy)
    }

    fun loadFail(it: Throwable) {
        if (mView.isRefreshing()) {
            mView.stopRefresh()
        }

        if (mView.isLoadingMore()) {
            mView.stopLoadingdMore(false)
        }

        if (datasource.isEmpty()) {
            mView?.showRecyclerViewError(it)
        }
    }

    fun loadSuccess(resultData: Any? = null) {
        val isFinish = mPagingStrategy?.checkFinish(resultData, pagingList.size) ?: true
        if (mView.isRefreshing()) {
            mView.stopRefresh()
        }
        mView.stopLoadingdMore(isFinish)
        if (datasource.isEmpty()) {
            mView?.showRecyclerViewEmpty()
        } else {
            mView?.setRecyclerViewData(datasource)
            mView?.showRecyclerViewContent()
        }
        mPagingStrategy?.addPage(null)
    }


}