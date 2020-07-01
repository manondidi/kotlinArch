package com.czq.kotlin_arch.basePage.base

interface IBasePagingView : IBaseView {

    fun showRecyclerViewEmpty()

    fun showRecyclerViewError(it: Throwable? = null)

    fun showRecyclerViewContent()

    fun showRecyclerViewLoading()

    fun beginRefresh()

    fun stopRefresh()

    fun stopLoadingdMore(finish: Boolean)

    fun isRefreshing(): Boolean

    fun isLoadingMore(): Boolean

    fun setRecyclerViewData(datasource: ArrayList<Any>)


    fun notifyDataChange()
}