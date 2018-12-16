package com.czq.kotlinarch.example.fragment

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.czq.kotlin_arch.basePage.paging.BasePagingFragment
import com.czq.kotlin_arch.common.util.DensityUtil
import com.czq.kotlinarch.R
import com.czq.kotlinarch.data.viewModel.ChallengeRecommandItemVm
import com.czq.kotlinarch.viewbinder.ChallengeViewbinder
import com.czq.kotlinarch.viewbinder.CircleViewbinder
import com.czq.kotlinarch.viewbinder.SeeMoreViewbinder
import kotlinx.android.synthetic.main.fragment_paging_list.*
import me.drakeet.multitype.register

class PagingListFragment : BasePagingFragment<PagingListPresenter>(), PagingListContact.PagingListView {

    companion object {
        fun newInstance():PagingListFragment{
            return PagingListFragment()
        }
    }

    override fun registItemBinder() {
        multiAdapter.register(ChallengeViewbinder())
        multiAdapter.register(CircleViewbinder())
        multiAdapter.register(SeeMoreViewbinder())
    }

    override fun createPresenter(): PagingListPresenter {
        return PagingListPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_paging_list
    }

    override fun initView() {
        super.initView()
        val layoutManager = GridLayoutManager(context,2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (multiAdapter.items[position] is ChallengeRecommandItemVm)
                    return 1
                else {
                    return 2
                }
            }
        }
        pagingRecycleview.layoutManager=layoutManager
        pagingRecycleview.addItemDecoration(SpaceItemDecoration())//为了弄间距
    }

    inner class SpaceItemDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            val position = parent!!.getChildLayoutPosition(view)

            if (multiAdapter.items[position] is ChallengeRecommandItemVm) {
                val item = multiAdapter.items[position] as ChallengeRecommandItemVm
                if (item.index % 2 == 0) {
                    outRect?.left = DensityUtil.dip2px(getContext(),15f)
                    outRect?.right = DensityUtil.dip2px(getContext(),5f)
                } else {
                    outRect?.left = DensityUtil.dip2px(getContext(),5f)
                    outRect?.right = DensityUtil.dip2px(getContext(),15f)
                }
            } else {
                outRect?.left = 0
                outRect?.right = 0
            }
        }
    }

}