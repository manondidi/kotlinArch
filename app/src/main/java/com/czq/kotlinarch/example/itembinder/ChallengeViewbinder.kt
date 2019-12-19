package com.czq.kotlinarch.viewbinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.czq.kotlinarch.R
import com.czq.kotlinarch.data.viewModel.ChallengeRecommandItemVm
import com.drakeet.multitype.ItemViewBinder
import kotlinx.android.synthetic.main.challenge_home_item_challenge.view.*


class ChallengeViewbinder : ItemViewBinder<ChallengeRecommandItemVm, ChallengeViewbinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ChallengeViewbinder.ViewHolder =
        ViewHolder(inflater.inflate(R.layout.challenge_home_item_challenge, parent, false))

    override fun onBindViewHolder(holder: ChallengeViewbinder.ViewHolder, itemData: ChallengeRecommandItemVm) {
        holder.itemView.tvScore.text = "${itemData?.candidatePoint}积分"
        holder.itemView.tvTitle.text = itemData?.title
        holder.itemView.tvCount.text = "${itemData?.candidateCount}条有料 ${itemData?.takeOnCount}人参与"
        Glide.with(holder.itemView.context).load(itemData?.cover)
            .apply(bitmapTransform(MultiTransformation(CenterCrop())))
            .into(holder.itemView.ivCover!!)

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}


}