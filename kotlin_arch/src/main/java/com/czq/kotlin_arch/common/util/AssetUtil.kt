package com.czq.kotlin_arch.common.util

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


class AssetUtil {
   companion object {
       fun getStringFromFile(context: Context, fileName: String): String? {
           val content = StringBuilder()
           var bufferedReader: BufferedReader? = null
           var inputStream: InputStream? = null
           val assetManager = context.getAssets()
           try {
               inputStream = assetManager.open(fileName)
               bufferedReader = BufferedReader(InputStreamReader(inputStream))
               var line: String
               while (true) {
                   line = bufferedReader.readLine() ?: break
                   content.append(line)
               }
               bufferedReader!!.close()
               inputStream!!.close()
               return content.toString()
           } catch (e: IOException) {
               e.printStackTrace()
           } finally {
               try {
                   if (bufferedReader != null) {
                       bufferedReader!!.close()
                   }
                   if (inputStream != null) {
                       inputStream!!.close()
                   }
               } catch (e: IOException) {
                   e.printStackTrace()
               }

           }
           return null
       }
   }

}