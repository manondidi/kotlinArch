package com.czq.kotlin_arch.basePage.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import org.jetbrains.annotations.NotNull


interface IBasePrensenter : LifecycleObserver {
    fun start()

}