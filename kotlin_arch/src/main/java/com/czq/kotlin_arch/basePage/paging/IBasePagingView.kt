package com.czq.kotlin_arch.basePage.base

import com.czq.kotlin_arch.paging.PagingStrategy

interface IBasePagingView:IBaseView {

    fun showRecyclerViewEmpty()

    fun showRecyclerViewError()

    fun showRecyclerViewContent()

    fun showRecyclerViewLoading()

    fun beginRefresh()

    fun stopRefresh()

    fun stopLoadingdMore(finish: Boolean)

    fun isRefreshing(): Boolean

    fun isLoadingMore(): Boolean

    fun setRecyclerViewData(datasource: ArrayList<Any>)



}