package com.czq.kotlinarch.viewbinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.czq.kotlinarch.R
import com.czq.kotlinarch.data.viewModel.ChallengeRecommandSeeMoreVm
import me.drakeet.multitype.ItemViewBinder


class SeeMoreViewbinder : ItemViewBinder<ChallengeRecommandSeeMoreVm, SeeMoreViewbinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): SeeMoreViewbinder.ViewHolder =
        ViewHolder(inflater.inflate(R.layout.challenge_home_item_see_more, parent, false))

    override fun onBindViewHolder(holder: SeeMoreViewbinder.ViewHolder, itemData: ChallengeRecommandSeeMoreVm) {
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}


}
