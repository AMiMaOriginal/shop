package com.AMiMa.shop.database.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(var name: String, var price: Int, var category: String, var count: Int = 1, @PrimaryKey(autoGenerate = true) var id: Int? = null){
    override fun equals(other: Any?) = (other is Product)
            && name == other.name
            && price == other.price
            && category == other.category
}
