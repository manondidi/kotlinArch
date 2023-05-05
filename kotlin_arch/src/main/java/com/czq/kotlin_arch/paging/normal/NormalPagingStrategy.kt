package com.czq.kotlin_arch.paging.normal

import com.czq.kotlin_arch.paging.PagingStrategy
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaField

class NormalPagingStrategy constructor(var startPageNum: Int = 0, var pageSize: Int = 20) :
    PagingStrategy {

      var pageInfo: NormalPagingInfo = NormalPagingInfo()


    fun initData() {
        pageInfo = NormalPagingInfo()
        pageInfo.startPageNum = this.startPageNum
        pageInfo.pageNum = startPageNum;
        pageInfo.pageSize = pageSize
    }


    override fun addPage(info: Any?) {
        pageInfo.pageNum += 1
    }

    override fun resetPage() {
        initData()
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