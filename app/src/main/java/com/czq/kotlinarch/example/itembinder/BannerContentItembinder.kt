package com.czq.kotlinarch.example.itembinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.czq.kotlinarch.R
import com.czq.kotlinarch.R.id.ivFirefly
import com.czq.kotlinarch.data.model.Banner
import com.drakeet.multitype.ItemViewBinder
import kotlinx.android.synthetic.main.recyclerview_item_banner_content.view.*


class BannerContentItembinder : ItemViewBinder<Banner, BannerContentItembinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BannerContentItembinder.ViewHolder =
            ViewHolder(inflater.inflate(R.layout.recyclerview_item_banner_content, parent, false))

    override fun onBindViewHolder(holder: BannerContentItembinder.ViewHolder, itemData: Banner) {
        val itemView = holder.itemView
        Glide.with(holder.itemView.context).load(itemData?.cover ?: "")
                .apply(bitmapTransform(MultiTransformation(CenterCrop())))
                .into(itemView.ivCover!!)
        if (itemData.relatedType == "article") {
            itemView.tvTitle.text = itemData.title

            itemView.tvTitle.visibility = View.VISIBLE
            itemView.tvGameName.visibility = View.GONE
            itemView.tvGameDesc.visibility = View.GONE
            itemView.ivFirefly.visibility = View.GONE
        } else {
            itemView.tvTitle.visibility = View.GONE
            itemView.tvGameName.visibility = View.VISIBLE
            itemView.tvGameDesc.visibility = View.VISIBLE

            itemView.tvGameName.text = itemData.title
            itemView.tvGameDesc.text = itemData.summary
            itemView.ivFirefly.visibility = if (itemData.isIsFirefly) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}


}
