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
import com.czq.kotlinarch.data.viewModel.ChallengeRecomandCircleVm
import com.drakeet.multitype.ItemViewBinder
import kotlinx.android.synthetic.main.challenge_home_item_circle.view.*

class CircleViewbinder : ItemViewBinder<ChallengeRecomandCircleVm, CircleViewbinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): CircleViewbinder.ViewHolder =
        ViewHolder(inflater.inflate(R.layout.challenge_home_item_circle, parent, false))

    override fun onBindViewHolder(holder: CircleViewbinder.ViewHolder, itemData: ChallengeRecomandCircleVm) {
        holder.itemView.tvSubscribe.visibility = if (itemData?.hasSubscribed ?: false) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
        holder.itemView.tvName.text = itemData?.title
        holder.itemView.tvCount.text = "共${itemData?.subscribedCount}个圈友,${itemData?.challengeCount}个挑战"
        Glide.with(holder.itemView.context).load(itemData?.icon)
            .apply(bitmapTransform(MultiTransformation(CenterCrop())))
            .into(holder.itemView.ivIcon!!)

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}


}