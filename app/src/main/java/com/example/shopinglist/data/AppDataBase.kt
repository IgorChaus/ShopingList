package com.example.shopinglist.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShopItemDbModel::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {

    abstract fun shopListDao(): ShopListDao

    companion object{
        private var INSTANCE: AppDataBase? = null
        private var LOCK = Any()
        private const val DB_NAME = "shop_item.db"

        fun getInstense(application: Application): AppDataBase{

            INSTANCE?.let{
                return it
            }

            // Такой способ реализации SINGLETON называется DOUBLECHECK
            synchronized(LOCK){
                INSTANCE?.let{
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    AppDataBase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = db
                return db
            }
        }
    }
}