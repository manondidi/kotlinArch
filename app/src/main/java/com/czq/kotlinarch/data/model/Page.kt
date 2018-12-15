package com.czq.kotlinarch.data.model

class Page<T> {
    var totalNum: Int = 0
    var pageNo: Int = 0
    var pageSize: Int = 0
    var listData: List<T>? = null
}