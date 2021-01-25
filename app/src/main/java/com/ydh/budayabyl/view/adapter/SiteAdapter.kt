package com.ydh.budayabyl.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ydh.budayabyl.R
import com.ydh.budayabyl.databinding.ItemMainBinding
import com.ydh.budayabyl.model.Site

class SiteAdapter(
    private val context: Context,
    private val listener: SiteListener
): RecyclerView.Adapter<SiteAdapter.ViewHolder>(){

    var list = mutableListOf<Site>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    interface SiteListener{
        fun onClick(model: Site)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteAdapter.ViewHolder {
        return ViewHolder(ItemMainBinding.inflate(LayoutInflater.from(context), parent, false))

    }

    override fun onBindViewHolder(holder: SiteAdapter.ViewHolder, position: Int) {
        val model by lazy {
            list[position]
        }

        holder.bindData(model)
        holder.itemMainBinding.run {
            ivSiteImage.setOnClickListener { listener.onClick(model) }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

   inner class ViewHolder(
        val itemMainBinding: ItemMainBinding
    ): RecyclerView.ViewHolder(itemMainBinding.root){
        fun bindData(siteModel: Site) {
            itemMainBinding.run {
                tvSiteName.text = siteModel.name_id
                Glide
                    .with(context)
                    .load(siteModel.image)
                    .into(ivSiteImage)
            }
        }
    }

}