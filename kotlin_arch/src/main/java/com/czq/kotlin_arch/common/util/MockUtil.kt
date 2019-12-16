package com.czq.kotlin_arch.common.util

import android.content.Context
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference

class MockUtil {
    companion object {
        inline fun <reified T> getMockModelList(context: Context, fileName: String, clazz: Class<T> ): List<T> {
            val jsonStr = AssetUtil.getStringFromFile(context, fileName)
            return JSON.parseArray(jsonStr,clazz)
        }

        inline fun <reified T> getMockModel(context: Context, fileName: String, clazz: Class<T> ): T {
            val jsonStr = AssetUtil.getStringFromFile(context, fileName)
            return JSON.parseObject(jsonStr,clazz)
        }

        inline fun <reified T> getMockModel(context: Context, fileName: String, typeReference: TypeReference<T> ): T {
            val jsonStr = AssetUtil.getStringFromFile(context, fileName)
            return JSON.parseObject(jsonStr,typeReference)
        }
    }
}