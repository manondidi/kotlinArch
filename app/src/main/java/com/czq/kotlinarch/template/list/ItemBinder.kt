package com.czq.kotlinarch.template.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.czq.kotlinarch.R
import com.drakeet.multitype.ItemViewBinder


class ItemBinder : ItemViewBinder<Item, ItemBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder =
        ViewHolder(inflater.inflate(R.layout.item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, itemData: Item) {
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}


}
