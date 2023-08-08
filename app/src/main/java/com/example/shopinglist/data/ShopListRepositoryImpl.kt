package com.example.shopinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.example.shopinglist.domain.ShopItem
import com.example.shopinglist.domain.ShopListRepository

class ShopListRepositoryImpl(
    application: Application
): ShopListRepository  {

    private val shopListDao = AppDataBase.getInstense(application).shopListDao()
    private val mapper = ShopListMapper()

    override fun addShopItem(shopItem: ShopItem) {
       shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

    override fun editShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
       val dbModel = shopListDao.getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getShopList(): LiveData<List<ShopItem>> = Transformations.map(
        shopListDao.getShopList()){
        mapper.mapListDbModelToListEntity(it)
    }

}