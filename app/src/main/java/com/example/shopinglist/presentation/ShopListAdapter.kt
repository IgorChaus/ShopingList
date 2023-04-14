package com.example.shopinglist.presentation

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinglist.R
import com.example.shopinglist.domain.ShopItem

class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var shopList = listOf<ShopItem>()
        set(value){
            val callback = ShopListDiffCallBack(shopList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null



    class ShopItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
    }

    override fun getItemViewType(position: Int): Int {
        val itemShopList = shopList[position]
        return  if (itemShopList.enabled)
                    R.layout.item_shop_enabled
                else
                    R.layout.item_shop_disabled

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val view: View
        if (viewType == R.layout.item_shop_enabled)
            view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_shop_enabled,
                parent,
                false
            )
        else
            view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_shop_disabled,
            parent,
            false
        )
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()
        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        holder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }

    }

    override fun getItemCount(): Int {
       return shopList.size
    }

    companion object{
        const val MAX_PULL_SIZE = 5
    }
}