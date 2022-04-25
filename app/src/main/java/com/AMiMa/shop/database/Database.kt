package com.AMiMa.shop.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.AMiMa.shop.database.dao.UserDao
import com.AMiMa.shop.database.dataClasses.User

@Database(entities = [User::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract fun usersDao(): UserDao

    companion object{
        var instance: com.AMiMa.shop.database.Database? = null

        fun getDatabase(context: Context): com.AMiMa.shop.database.Database?{
            if (instance == null)
                synchronized(this) {
                    instance = Room.databaseBuilder(context, com.AMiMa.shop.database.Database::class.java, "ShopDB").build()
                }
            return instance!!
        }
    }
}