package com.example.shopinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinglist.R
import com.example.shopinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {
    private lateinit var  viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.shopList.observe(this){
            shopListAdapter.shopList = it
        }
    }

    fun setupRecyclerView(){
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        with(rvShopList){
            shopListAdapter = ShopListAdapter()
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(R.layout.item_shop_enabled
                ,ShopListAdapter.MAX_PULL_SIZE)
            recycledViewPool.setMaxRecycledViews(R.layout.item_shop_disabled
                ,ShopListAdapter.MAX_PULL_SIZE)
        }
        /*shopListAdapter.onShopItemLongClickListener = object: ShopListAdapter.OnShopItemLongClickListener{
            override fun onShopItemLongClick(shopItem: ShopItem) {
                viewModel.changeEnableState(shopItem)
            }
        }*/

        shopListAdapter.onShopItemLongClickListener = {
                viewModel.changeEnableState(it)
        }

    }



}