package com.czq.kotlinarch.template.list

import com.czq.kotlin_arch.basePage.paging.BasePagingFragment
import com.czq.kotlinarch.R

class ListFragment : BasePagingFragment<ListContact.PagingListPresenter>(), ListContact.PagingListView {

    companion object {
        fun newInstance():ListFragment{
            return ListFragment()
        }
    }

    override fun registItemBinder() {
        multiAdapter.register(ItemBinder())
    }

    override fun createPresenter(): ListContact.PagingListPresenter {
        return ListPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_paging_list
    }

    override fun initView() {
        super.initView()
    }


}