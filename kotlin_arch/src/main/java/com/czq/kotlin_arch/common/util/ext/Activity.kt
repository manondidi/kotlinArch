package com.czq.kotlin_arch.common.util.ext

import android.content.res.Resources
import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.hideBottom() {
    //隐藏虚拟按键，并且全屏
    if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
        val v = window.decorView
        v.systemUiVisibility = View.GONE
    } else if (Build.VERSION.SDK_INT >= 19) { //for new api versions.
        val decorView = window.decorView
        val uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        decorView.systemUiVisibility = uiOptions
    }
}