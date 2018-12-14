package com.czq.kotlin_arch.paging.normal

import com.czq.kotlin_arch.paging.PagingStrategy
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaField

class NormalPagingStrategy constructor(startPageNum: Int = 0, pageSize: Int = 20) : PagingStrategy {

    val pageInfo: NormalPagingInfo =
        NormalPagingInfo()

    init {
        pageInfo.startPageNum = startPageNum
        pageInfo.pageNum = startPageNum;
        pageInfo.pageSize = pageSize
    }


    override fun addPage(info: Any?) {
        pageInfo.pageNum += 1
    }

    override fun resetPage() {
        pageInfo.pageNum = pageInfo.startPageNum
    }

    override fun getPageInfo(): Any {
        return pageInfo
    }

    override fun checkFinish(result: Any?, listSize: Int): Boolean {
        if (result == null) {
            return true
        }
        val count = result::class.java.declaredFields.find {
            it.isAccessible = true
            it.name == "totalNum"
        }?.get(result)
        pageInfo.totalCount = count as? Int ?: 0
        return pageInfo.totalCount <= listSize
    }


}