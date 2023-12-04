package com.AMiMa.shop.database.dataClasses

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(var description: String, var price: Double,
                   var category: String, var imageURL: String,
                   var count: Int = 1, @PrimaryKey(autoGenerate = true) var id: Int? = null){

    override fun equals(other: Any?) = (other is Product)
            && description == other.description
            && price == other.price
            && category == other.category

    override fun hashCode(): Int {
        var result = description.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + category.hashCode()
        return result
    }
}