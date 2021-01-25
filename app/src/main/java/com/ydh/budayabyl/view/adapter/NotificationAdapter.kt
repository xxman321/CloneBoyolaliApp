package com.ydh.budayabyl.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ydh.budayabyl.databinding.ItemMainBinding
import com.ydh.budayabyl.databinding.ItemNotificationBinding
import com.ydh.budayabyl.model.Notification
import com.ydh.budayabyl.model.Site

class NotificationAdapter (
    private val context: Context,
    private val itemListener: NotificationListener
): RecyclerView.Adapter<NotificationAdapter.ViewHolder>(){
    var list = mutableListOf<Notification>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    interface NotificationListener{
        fun onClick(model: Notification)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationAdapter.ViewHolder {
        return ViewHolder(ItemNotificationBinding.inflate(LayoutInflater.from(context), parent, false))

    }

    override fun onBindViewHolder(holder: NotificationAdapter.ViewHolder, position: Int) {
        val model by lazy {
            list[position]
        }

        holder.bindData(model)
        holder.itemMainBinding.run {
            root.setOnClickListener {
                model.isViewed = true
                notifyItemChanged(position)
                itemListener.onClick(model)
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(
        val itemMainBinding: ItemNotificationBinding
    ): RecyclerView.ViewHolder(itemMainBinding.root){
        fun bindData(notification: Notification) {
            itemMainBinding.run {
                tvItemNotificationTitle.text = notification.title
                tvItemNotificationContent.text = notification.content
                ivItemNotificationRedDot.visibility = if (notification.isViewed) View.INVISIBLE else View.VISIBLE
            }
        }
    }


}