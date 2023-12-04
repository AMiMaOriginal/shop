package com.AMiMa.shop.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.AMiMa.shop.R
import com.AMiMa.shop.database.Database
import com.AMiMa.shop.database.dao.ProductDao
import com.AMiMa.shop.database.dao.ReviewDao
import com.AMiMa.shop.database.dataClasses.Product
import com.AMiMa.shop.database.dataClasses.Review
import com.AMiMa.shop.model.CurrentUserInfo
import com.AMiMa.shop.model.ReviewModel
import com.AMiMa.shop.model.WorkInCart
import com.AMiMa.shop.ui.adapters.AdapterForReview
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detailed_product.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.URL

class DetailedProduct : AppCompatActivity(), View.OnClickListener {

    private lateinit var nameProduct: TextView
    private lateinit var priceProduct: TextView
    private lateinit var buyProduct: Button
    private lateinit var pushReview: Button
    private lateinit var imageProduct: ImageView
    private lateinit var reviewText: EditText
    private lateinit var product: Product
    private lateinit var reviewModel: ReviewModel
    private lateinit var adapter: AdapterForReview
    private var dataForAdapter = mutableListOf<Review>()
    private lateinit var viewCountProduct: TextView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_product)

        reviewModel = ReviewModel(this)

        nameProduct = findViewById(R.id.nameProduct)
        priceProduct = findViewById(R.id.priceProduct)
        imageProduct = findViewById(R.id.imageProduct)
        viewCountProduct = findViewById(R.id.countProduct)
        reviewText = findViewById(R.id.writeReview)
        buyProduct = findViewById(R.id.buyProduct)
        buyProduct.setOnClickListener(this@DetailedProduct)
        pushReview = findViewById(R.id.pushReviews)
        if (CurrentUserInfo.getInstance().getUser() == null){
            pushReview.isClickable = false
            pushReview.setBackgroundColor(Color.GRAY)
            reviewText.hint = "Войдите, чтобы оставить отзыв"
        }

        loadInfo()

        initRecyclerView()
    }

    fun changeCountProduct(view: View) {
        if (view.id == R.id.countPlus)
            product.count++
        else
            product.count--

        if (product.count < 1)
            product.count = 1

        viewCountProduct.text = product.count.toString()
    }

    private fun initRecyclerView() {
        val listReviews: RecyclerView = findViewById(R.id.listReviews)
        listReviews.layoutManager = LinearLayoutManager(this@DetailedProduct)
        adapter = AdapterForReview(dataForAdapter)
        listReviews.adapter = adapter
        updateReviews()
    }

    fun pushReview(view: View){
        if (reviewModel.pushReview(Review(CurrentUserInfo.getInstance().getUser()!!.name, reviewText.text.toString(), product.id!!))) {
            reviewText.setText("")
            updateReviews()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateReviews() {
        dataForAdapter.clear()
        dataForAdapter.addAll(reviewModel.getReviewsForProduct(product.id!!))
        adapter.notifyDataSetChanged()
    }

    private fun loadInfo() = runBlocking {
        product = Gson().fromJson(intent.getStringExtra("product"), Product::class.java)
        nameProduct.text = product.description
        priceProduct.text = product.price.toString()

        launch(Dispatchers.IO) {
            val url = URL(product.imageURL)
            val input = url.openStream()
            imageProduct.setImageBitmap(BitmapFactory.decodeStream(input))
        }
    }

    override fun onClick(p0: View?) {
        val cart = WorkInCart.getInstance()
        cart.addInListProducts(product)
        finish()
    }
}