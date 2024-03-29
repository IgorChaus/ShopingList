package com.example.shopinglist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShopListDao {

    @Query("SELECT * FROM shop_items")
    fun getShopList(): LiveData<List<ShopItemDbModel>>

//    Можно вернуть просто List. ArrayList возвращать не умеет
//    @Query("SELECT * FROM shop_items")
//    fun getShopList(): List<ShopItemDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //Если у нас будет добавлен Item с тем же самым id, то он перезапимшется в таблице
    suspend fun addShopItem(shopItemDbModel: ShopItemDbModel)

    @Query("DELETE FROM shop_items WHERE id=:shopItemId")
    suspend fun deleteShopItem(shopItemId: Int)

    @Query("SELECT * FROM shop_items WHERE id=:shopItemId LIMIT 1")
    suspend fun getShopItem(shopItemId: Int): ShopItemDbModel
}