package com.czq.kotlinarch

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import android.content.Context
import android.view.Gravity
import com.czq.kotlin_arch.component.cover.CoverFrameViewConfig
import com.hjq.toast.ToastUtils
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator


class App : Application() {
    companion object {
        lateinit var application: App
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        ToastUtils.init(this)
        ToastUtils.setGravity(Gravity.BOTTOM, 0, 0)
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(object : DefaultRefreshHeaderCreator {
            override fun createRefreshHeader(
                context: Context,
                layout: RefreshLayout
            ): RefreshHeader {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)//全局设置主题颜色
                return ClassicsHeader(context)//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        })
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(object : DefaultRefreshFooterCreator {
            override fun createRefreshFooter(
                context: Context,
                layout: RefreshLayout
            ): RefreshFooter {
                //指定为经典Footer，默认是 BallPulseFooter
                return ClassicsFooter(context).setDrawableSize(20f)
            }
        })
        //这里可以配置全局loadingview emptyview errorview的布局
        CoverFrameViewConfig.defaultLoadingViewLayout = R.layout.custom_loading_view

    }


}