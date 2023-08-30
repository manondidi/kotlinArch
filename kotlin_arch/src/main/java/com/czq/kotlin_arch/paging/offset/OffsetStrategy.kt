package com.czq.kotlin_arch.paging.offset

import com.czq.kotlin_arch.paging.PagingStrategy

class OffsetStrategy(var pageSize: Int = 20, var offsetIdKey: String) : PagingStrategy {

    var pageInfo: OffsetPageInfo = OffsetPageInfo()


    fun initData() {
        pageInfo = OffsetPageInfo()
        pageInfo.pageSize = pageSize
        pageInfo.type = "new"
        pageInfo.offsetId = null
    }

    override fun addPage(info: Any?) {

        pageInfo.type = "old"
        val arr = info as? List<Any>
        val obj = arr?.last()
        if (obj != null) {
            pageInfo.offsetId = obj::class.java.declaredFields.find {
                it.isAccessible = true
                it.name == offsetIdKey
            }?.get(obj)?.toString()
        }
    }

    override fun resetPage() {
        initData()
    }

    override fun getPageInfo(): Any {
        return pageInfo
    }

    override fun checkFinish(result: Any?, listSize: Int): Boolean {
        val arr = result as? List<*>
        return arr?.size == 0
    }


}