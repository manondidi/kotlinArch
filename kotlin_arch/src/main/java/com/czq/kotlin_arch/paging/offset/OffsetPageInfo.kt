package com.czq.kotlin_arch.paging.offset

class OffsetPageInfo  {
    var type = "new"
    var pageSize = 20
    var offsetId: String? = null

    init {
        this.pageSize = pageSize
    }

    fun isFirstPage(): Boolean {
        return type == "new"
    }
}