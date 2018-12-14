package com.czq.kotlin_arch.basePage.base

import com.czq.kotlin_arch.paging.PagingStrategy

interface IBasePagingPrensenter : IBasePrensenter {


    fun getPagingStrategy(): PagingStrategy?

    fun onLoadData(pagingStrategy: PagingStrategy?)

    fun resetPage()
    
    fun loadData()
}