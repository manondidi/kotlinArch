package com.czq.kotlinarch.data.model

class Result<T> {
    var status: Int = 0
    var msg: String? = null
    var data: T? = null
}
