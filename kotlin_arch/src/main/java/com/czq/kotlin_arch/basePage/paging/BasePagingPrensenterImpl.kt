package com.czq.kotlin_arch.basePage.base

import com.czq.kotlin_arch.paging.PagingStrategy

abstract class BasePagingPrensenterImpl(open val mView: IBasePagingView) : IBasePrensenter {


    val datasource = arrayListOf<Any>()
    val pagingList = arrayListOf<Any>()

    var mPagingStrategy: PagingStrategy? = null


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
        if (mView.isRefreshLoading()) {
            mView.stopRefresh()
        }

        if (mView.isRefreshLoading()) {
            mView.stopLoadingdMore(false)
        }

        if (datasource.isEmpty()) {
            mView?.showRecyclerViewError(it)
        }
    }

    fun loadSuccess(resultData: Any? = null) {
        val isFinish = mPagingStrategy?.checkFinish(resultData, pagingList.size) ?: true
        if (mView.isRefreshLoading()) {
            mView.stopRefresh()
        }
        mView.stopLoadingdMore(isFinish)
        if (datasource.isEmpty()) {
            mView?.showRecyclerViewEmpty()
        } else {
            mView?.setRecyclerViewData(datasource)
            mView?.showRecyclerViewContent()
        }
        mPagingStrategy?.addPage(resultData)
    }


}