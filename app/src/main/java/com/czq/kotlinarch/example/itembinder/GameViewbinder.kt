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
import com.czq.kotlinarch.data.model.Game
import kotlinx.android.synthetic.main.game_item.view.*
import me.drakeet.multitype.ItemViewBinder


class GameViewbinder : ItemViewBinder<Game, GameViewbinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): GameViewbinder.ViewHolder =
        ViewHolder(inflater.inflate(R.layout.game_item, parent, false))

    override fun onBindViewHolder(holder: GameViewbinder.ViewHolder, itemData: Game) {
        Glide.with(holder.itemView.context).load(itemData?.icon)
            .apply(bitmapTransform(MultiTransformation(CenterCrop())))
            .into(holder.itemView.ivIcon!!)
        holder.itemView.tvName.text = itemData.title
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}


}
