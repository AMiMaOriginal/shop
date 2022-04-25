package com.AMiMa.shop.database.dao

import androidx.room.*
import com.AMiMa.shop.database.dataClasses.User

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM User WHERE name = :name")
    fun getUser(name: String): User?

    @Query("SELECT * FROM User")
    fun getAllUsers(): List<User>
}