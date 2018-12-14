package com.czq.kotlin_arch.paging.normal

class NormalPagingInfo {

    var pageSize: Int = 0
    var pageNum: Int = 0
    var totalCount: Int = 0
    var startPageNum: Int = 0//从多少开始计算的 一般分页要么是0 要么是1

    fun isFirstPage(): Boolean {
        return startPageNum == pageNum
    }
}