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
import com.czq.kotlinarch.data.model.FeedArticle
import com.hwangjr.rxbus.RxBus
import kotlinx.android.synthetic.main.recyclerview_item_feed_article.view.*
import me.drakeet.multitype.ItemViewBinder


class FeedArticleItembinder : ItemViewBinder<FeedArticle, FeedArticleItembinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): FeedArticleItembinder.ViewHolder =
        ViewHolder(inflater.inflate(R.layout.recyclerview_item_feed_article, parent, false))

    override fun onBindViewHolder(holder: FeedArticleItembinder.ViewHolder, itemData: FeedArticle) {
        val itemView = holder.itemView
        Glide.with(holder.itemView.context).load(itemData?.user?.avatar ?: "")
            .apply(bitmapTransform(MultiTransformation(CenterCrop())))
            .into(itemView.ivAvatar!!)
        Glide.with(holder.itemView.context).load(itemData?.payload?.article?.cover?.medium ?: "")
            .apply(bitmapTransform(MultiTransformation(CenterCrop())))
            .into(itemView.ivCover!!)
        itemView.tvTitle.text = itemData.payload?.article?.title ?: ""
        itemView.tvUserName.text = itemData.user?.nickname ?: ""
        itemView.tvGameName.text = itemData.payload?.article?.game?.name ?: ""
        val isFirefly = itemData.payload?.article?.isIsFireflyUser ?: false
        itemView.tvI.visibility = if (isFirefly) {
            View.VISIBLE
        } else {
            View.GONE
        }
        itemView.tvFrom.visibility = if (isFirefly) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                RxBus.get().post("hi","")
            }
        }
    }
}