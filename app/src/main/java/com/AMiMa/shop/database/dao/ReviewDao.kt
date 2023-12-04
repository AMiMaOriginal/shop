package com.AMiMa.shop.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.AMiMa.shop.database.dataClasses.Review

@Dao
interface ReviewDao {
    @Insert
    fun addReview(review: Review)

    @Delete
    fun deleteReview(review: Review)

    @Query("SELECT * FROM Review WHERE productId = :id")
    fun getReviewsByProductId(id: Int) : MutableList<Review>
}