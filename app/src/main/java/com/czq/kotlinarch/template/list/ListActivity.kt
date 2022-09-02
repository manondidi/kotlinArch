package com.czq.kotlinarch.template.list

import com.czq.kotlin_arch.basePage.paging.BasePagingActivity
import com.czq.kotlinarch.R


class ListActivity : BasePagingActivity<ListContact.PagingListPresenter>(),
    ListContact.PagingListView {


    override fun registItemBinder() {
        multiAdapter.register(ItemBinder())

    }

    override fun initView() {
        super.initView()

    }




    override fun getLayoutId(): Int {
        return R.layout.activity_list
    }

    override fun createPresenter(): ListContact.PagingListPresenter {
        return ListPresenter(this)
    }


}