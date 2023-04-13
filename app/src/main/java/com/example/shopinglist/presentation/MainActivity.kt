package com.example.shopinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.shopinglist.R
import com.example.shopinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {
    private lateinit var  viewModel: MainViewModel
    private lateinit var llShopList: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        llShopList = findViewById(R.id.ll_shop_list)
        viewModel.shopList.observe(this){
            showList(it)
        }
    }

    private fun showList(list: List<ShopItem>){
        llShopList.removeAllViews()
        for(shopItem in list){
            val layoutID = if(shopItem.enabled)
                R.layout.item_shop_enabled
            else
                R.layout.item_shop_disabled

            val view = LayoutInflater.from(this).inflate(layoutID,llShopList,false)
            val tvname = view.findViewById<TextView>(R.id.tv_name)
            val tvcount = view.findViewById<TextView>(R.id.tv_count)
            tvname.text = shopItem.name
            tvcount.text = shopItem.count.toString()
            view.setOnLongClickListener{
                viewModel.changeEnableState(shopItem)
                true
            }
            llShopList.addView(view)

        }
    }
}