package com.czq.kotlinarch.viewbinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.czq.kotlinarch.R
import com.czq.kotlinarch.data.viewModel.GameDate
import kotlinx.android.synthetic.main.game_item_date.view.*
import me.drakeet.multitype.ItemViewBinder


class GameDateViewbinder : ItemViewBinder<GameDate, GameDateViewbinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): GameDateViewbinder.ViewHolder =
        ViewHolder(inflater.inflate(R.layout.game_item_date, parent, false))

    override fun onBindViewHolder(holder: GameDateViewbinder.ViewHolder, itemData: GameDate) {
        holder.itemView.tvDate.text = itemData.date
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}


}
