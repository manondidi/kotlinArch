package com.czq.kotlin_arch.paging.offset

import com.czq.kotlin_arch.paging.PagingStrategy

class OffsetStrategy(pageSize: Int = 20, offsetIdKey: String) : PagingStrategy {

    var pageInfo: OffsetPageInfo = OffsetPageInfo()
    var offsetIdKey = ""

    init {
        pageInfo.pageSize = pageSize
        this.offsetIdKey = offsetIdKey
        this.pageInfo.type = "new"
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
        pageInfo.type = "new"
        pageInfo.offsetId = null
    }

    override fun getPageInfo(): Any {
        return pageInfo
    }

    override fun checkFinish(result: Any?, listSize: Int): Boolean {
        val arr = result as? List<*>
        return arr?.size == 0
    }


}