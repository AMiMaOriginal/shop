package com.AMiMa.shop.model

import android.content.Context
import com.AMiMa.shop.database.Database
import com.AMiMa.shop.database.dao.ReviewDao
import com.AMiMa.shop.database.dataClasses.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ReviewModel {

    private var database: ReviewDao

    constructor(context: Context) {
        database = Database.getDatabase(context)!!.reviewDao()
    }

    fun pushReview(review: Review) : Boolean {
        if (review.review.trim().isEmpty())
            return false

        runBlocking { launch(Dispatchers.IO) { database.addReview(review) } }
        return true
    }

    fun getReviewsForProduct(productId: Int) : MutableList<Review> {
        var listReview = mutableListOf<Review>()
        runBlocking{
            val a = launch(Dispatchers.IO) { listReview =  database.getReviewsByProductId(productId) }
            a.start()
            a.join()
        }
        return listReview
    }
}