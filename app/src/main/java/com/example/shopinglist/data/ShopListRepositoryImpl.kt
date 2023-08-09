package com.example.shopinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.shopinglist.domain.ShopItem
import com.example.shopinglist.domain.ShopListRepository
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
    private val shopListDao: ShopListDao,
    private val mapper: ShopListMapper
): ShopListRepository  {

    override suspend fun addShopItem(shopItem: ShopItem) {
       shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem {
       val dbModel = shopListDao.getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

//    Класс Transformation был удален из библиотеки. Вместо него используется метод map
//    override fun getShopList(): LiveData<List<ShopItem>> = Transformations.map(
//        shopListDao.getShopList()){
//        mapper.mapListDbModelToListEntity(it)
//    }

    override fun getShopList(): LiveData<List<ShopItem>> = shopListDao.getShopList().map {
        mapper.mapListDbModelToListEntity(it)
    }

}