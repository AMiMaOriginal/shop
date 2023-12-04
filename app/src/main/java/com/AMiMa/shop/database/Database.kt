package com.AMiMa.shop.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.AMiMa.shop.database.dao.ProductDao
import com.AMiMa.shop.database.dao.ReviewDao
import com.AMiMa.shop.database.dao.UserDao
import com.AMiMa.shop.database.dataClasses.Product
import com.AMiMa.shop.database.dataClasses.Review
import com.AMiMa.shop.database.dataClasses.User
import kotlin.math.round

@Database(entities = [User::class, Product::class, Review::class], version = 7)
abstract class Database : RoomDatabase() {

    abstract fun usersDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun reviewDao(): ReviewDao

    companion object{
        var instance: com.AMiMa.shop.database.Database? = null

        fun getDatabase(context: Context): com.AMiMa.shop.database.Database?{
            if (instance == null)
                synchronized(this) {
                    instance = Room.databaseBuilder(context, com.AMiMa.shop.database.Database::class.java, "ShopDB")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            return instance!!
        }
    }
}