package com.AMiMa.shop.database.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Review(val author: String, val review: String, val productId: Int, @PrimaryKey(autoGenerate = true) val id: Int? = null)
