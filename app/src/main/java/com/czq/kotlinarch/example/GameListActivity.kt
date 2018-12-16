package com.czq.kotlinarch.example

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.czq.kotlin_arch.basePage.paging.BasePagingActivity
import com.czq.kotlin_arch.common.util.DensityUtil
import com.czq.kotlinarch.R
import com.czq.kotlinarch.data.viewModel.GameDate
import com.u17173.challenge.page.challenge.home.viewbinder.GameDateViewbinder
import com.u17173.challenge.page.challenge.home.viewbinder.GameViewbinder
import kotlinx.android.synthetic.main.activity_game_list.*
import me.drakeet.multitype.register


class GameListActivity : BasePagingActivity<GameListPresenter>(), GameListContact.PagingListView {

    val height by lazy {
        DensityUtil.dip2px(this, 40f) * 1f
    }

    override fun registItemBinder() {
        multiAdapter.register(GameViewbinder())
        multiAdapter.register(GameDateViewbinder())

    }

    override fun initView() {
        super.initView()


//        pagingRecycleview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
//                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
//                val firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
//                val gameDate = multiAdapter.items[firstVisibleItemPosition] as? GameDate
//                val view = layoutManager.findViewByPosition(firstVisibleItemPosition)
//                if (view == null) return
//                if (gameDate != null) {
//                    stickyContainer.setY(0f)
//                    tvDate111.text = gameDate?.date
//                } else {
//                    stickyContainer.setY(-(height - view.getTop()))
//                }
//
//            }
//        })

    }


    override fun createPresenter(): GameListPresenter {
        return GameListPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_game_list
    }


}