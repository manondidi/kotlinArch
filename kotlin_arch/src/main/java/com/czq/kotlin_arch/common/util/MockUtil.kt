package com.czq.kotlin_arch.common.util

import android.content.Context
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference

class MockUtil {
    companion object {
        inline fun <reified T> getMockModelList(context: Context, fileName: String, clazz: Class<T> ): List<T> {
            val jsonStr = AssetUtil.getStringFromFile(context, fileName)
            val parseArray = JSON.parseArray(jsonStr,clazz)
            return parseArray
        }

        inline fun <reified T> getMockModel(context: Context, fileName: String, clazz: Class<T> ): T {
            val jsonStr = AssetUtil.getStringFromFile(context, fileName)
            val parseObject = JSON.parseObject(jsonStr,clazz)
            return parseObject
        }

        inline fun <reified T> getMockModel(context: Context, fileName: String, typeReference: TypeReference<T> ): T {
            val jsonStr = AssetUtil.getStringFromFile(context, fileName)
            val parseObject = JSON.parseObject(jsonStr,typeReference)
            return parseObject
        }
    }
}