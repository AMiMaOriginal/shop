package com.AMiMa.shop.database.dao

import androidx.room.*
import com.AMiMa.shop.database.dataClasses.Product

@Dao
interface ProductDao {

    @Insert
    fun addProduct(product: Product)

    @Update
    fun updateProduct(product: Product)

    @Delete
    fun deleteProduct(product: Product)

    @Query("SELECT * FROM Product WHERE description = :name AND price = :price AND category = :category")
    fun getProduct(name: String, price: String, category: String): Product?

    @Query("SELECT * FROM Product")
    fun getAllProduct(): List<Product>

    @Query("SELECT * FROM Product WHERE category = :category")
    fun getAllProductByCategory(category: String): List<Product>
}