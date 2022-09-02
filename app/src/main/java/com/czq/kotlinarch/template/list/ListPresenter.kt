package com.czq.kotlinarch.template.list

import android.annotation.SuppressLint
import com.czq.kotlin_arch.basePage.base.BasePagingPrensenterImpl
import com.czq.kotlin_arch.paging.PagingStrategy

open class ListPresenter(mView: ListContact.PagingListView) : BasePagingPrensenterImpl(mView),
    ListContact.PagingListPresenter {


    override fun getPagingStrategy(): PagingStrategy? {
        return null
    }

    @SuppressLint("CheckResult")
    override fun onLoadData(pagingStrategy: PagingStrategy?) {

    }
}