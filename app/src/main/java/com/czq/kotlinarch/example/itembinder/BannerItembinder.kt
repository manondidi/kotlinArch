package com.czq.kotlinarch.example.itembinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.czq.kotlinarch.R
import com.czq.kotlinarch.data.viewModel.BannerList
import github.hellocsl.layoutmanager.gallery.GalleryLayoutManager
import kotlinx.android.synthetic.main.recyclerview_item_banner.view.*
import me.drakeet.multitype.ItemViewBinder
import me.drakeet.multitype.MultiTypeAdapter
import me.drakeet.multitype.register


class BannerItembinder : ItemViewBinder<BannerList, BannerItembinder.ViewHolder>() {


    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BannerItembinder.ViewHolder =
        ViewHolder(inflater.inflate(R.layout.recyclerview_item_banner, parent, false))

    override fun onBindViewHolder(holder: BannerItembinder.ViewHolder, itemData: BannerList) {

        holder.adapter.items = itemData?.banners!!
        holder.adapter.notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val adapter by lazy {
            val adp = MultiTypeAdapter()
            adp.register(BannerContentItembinder())
            adp
        }

        init {
            val layoutManager = GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL)
            layoutManager.attach(itemView.banner)

            layoutManager.setItemTransformer(ScaleTransformer())
            itemView.banner.setAdapter(adapter)

        }
    }

    class ScaleTransformer : GalleryLayoutManager.ItemTransformer {


        override fun transformItem(layoutManager: GalleryLayoutManager, item: View, fraction: Float) {
            item.pivotX = item.width / 2f
            item.pivotY = item.height / 2.0f
            val scale = 1 - 0.2f * Math.abs(fraction)
            item.scaleX = scale
            item.scaleY = scale
        }


    }


}
