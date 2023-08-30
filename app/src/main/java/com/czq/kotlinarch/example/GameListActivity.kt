package com.czq.kotlinarch.example

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.czq.kotlin_arch.basePage.paging.BasePagingActivity
import com.czq.kotlinarch.R
import com.czq.kotlinarch.data.viewModel.GameDate
import com.czq.kotlinarch.viewbinder.GameDateViewbinder
import com.czq.kotlinarch.viewbinder.GameViewbinder
import com.scwang.smart.refresh.header.BezierRadarHeader
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener
import kotlinx.android.synthetic.main.activity_game_list.*
import kotlinx.android.synthetic.main.game_item_date.view.*


class GameListActivity : BasePagingActivity<GameListContact.PagingListPresenter>(), GameListContact.PagingListView {


    override fun registItemBinder() {
        multiAdapter.register(GameViewbinder())
        multiAdapter.register(GameDateViewbinder())

    }

    override fun initView() {
        super.initView()

        preparStickyView()

        refreshLayout.setRefreshHeader(BezierRadarHeader(this))

        pagingRecycleview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (refreshLayout.state != RefreshState.None) return

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val gameDate = findNearHeadItem(firstVisibleItemPosition) as? GameDate
                if (gameDate != null) {
                    stickyHeaderView.visibility = View.VISIBLE
                    stickyHeaderView?.tvDate?.text = gameDate?.date
                } else {
                    stickyHeaderView.visibility = View.GONE
                }
            }
        })

    }

    private fun preparStickyView() {
        stickyHeaderView?.visibility = View.GONE
        refreshLayout.setOnMultiListener(object : SimpleMultiListener() {
            override fun onHeaderMoving(header: RefreshHeader?, isDragging: Boolean, percent: Float, offset: Int, headerHeight: Int, maxDragHeight: Int) {
                super.onHeaderMoving(header, isDragging, percent, offset, headerHeight, maxDragHeight)
                if (isDragging) {
                    stickyHeaderView?.visibility = View.GONE
                }
            }
        })
    }

    fun findNearHeadItem(position: Int): Any? {
        return multiAdapter.items.findLast {
            val b = it is GameDate && multiAdapter.items.indexOf(it) <= position
            b
        }
    }


    override fun createPresenter(): GameListContact.PagingListPresenter {
        return GameListPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_game_list
    }


}