package com.czq.kotlin_arch.paging

interface PagingStrategy {

    fun addPage(info:Any?)
    fun resetPage()
    fun getPageInfo():Any
    fun checkFinish(result:Any?,listSize: Int):Boolean
}