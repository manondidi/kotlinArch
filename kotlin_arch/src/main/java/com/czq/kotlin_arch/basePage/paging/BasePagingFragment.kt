package com.czq.kotlin_arch.basePage.paging

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.czq.kotlin_arch.R
import com.czq.kotlin_arch.basePage.base.BaseFragment
import com.czq.kotlin_arch.basePage.base.IBasePagingPrensenter
import com.czq.kotlin_arch.basePage.base.IBasePagingView
import com.czq.kotlin_arch.component.cover.CoverFrameLayout
import com.drakeet.multitype.MultiTypeAdapter
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import kotlinx.android.synthetic.main.fragment_base_paging.*

abstract class BasePagingFragment<T : IBasePagingPrensenter> : BaseFragment<T>(), IBasePagingView {

    val multiAdapter: MultiTypeAdapter = MultiTypeAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registItemBinder()
    }

    override fun initView() {
        super.initView()
        pagingRecycleview.layoutManager = LinearLayoutManager(context)
        pagingRecycleview.adapter = multiAdapter
        showRecyclerViewLoading()
        refreshLayout.setOnRefreshListener {
            mPresenter.resetPage()
            mPresenter.loadData()
        }
        refreshLayout.setOnLoadMoreListener {
            mPresenter.loadData()
        }

        coverLayout?.doReload = {
            beginRefresh()
        }
    }

    abstract fun registItemBinder()


    override fun getLayoutId(): Int {
        return R.layout.activity_base_paging
    }

    override fun showRecyclerViewEmpty() {
        showEmpty()
    }

    override fun showRecyclerViewError(it: Throwable?) {
        showError(it)
    }

    override fun showRecyclerViewContent() {
        showContent()
    }

    override fun showRecyclerViewLoading() {
        showLoading()
    }

    override fun isRefreshing(): Boolean {
        return refreshLayout.state == RefreshState.Refreshing
    }

    override fun isLoadingMore(): Boolean {
        return refreshLayout.state == RefreshState.Loading
    }


    override fun beginRefresh() {
        if (isRefreshing() || isLoadingMore()) {
            return
        }
        refreshLayout.autoRefresh()
    }

    override fun stopRefresh() {
        refreshLayout.finishRefresh()
    }

    override fun stopLoadingdMore(finish: Boolean) {
        if (finish) {
            refreshLayout.finishLoadMoreWithNoMoreData()
        } else {
            refreshLayout.finishLoadMore()
        }
    }

    override fun setRecyclerViewData(datasource: ArrayList<Any>) {
        multiAdapter.items = datasource
        multiAdapter.notifyDataSetChanged()
    }
}