package com.AMiMa.shop.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.AMiMa.shop.R
import com.AMiMa.shop.database.Database
import com.AMiMa.shop.database.dao.ProductDao
import com.AMiMa.shop.database.dataClasses.Product
import com.AMiMa.shop.model.WorkInCart

class DetailedProduct : AppCompatActivity(), View.OnClickListener {

    private lateinit var nameProduct: TextView
    private lateinit var priceProduct: TextView
    private lateinit var buyProduct: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_product)

        nameProduct = findViewById(R.id.nameProduct)
        priceProduct = findViewById(R.id.priceProduct)
        buyProduct = findViewById(R.id.buyProduct)
        buyProduct.setOnClickListener(this)

        loadInfo()
    }

    private fun loadInfo(){
        nameProduct.text = intent.getStringExtra("name")
        priceProduct.text = intent.getIntExtra("price", 0).toString()
    }

    override fun onClick(p0: View?) {
        val cart = WorkInCart.getInstance()
        cart.addInListProducts(Product(nameProduct.text.toString(), intent.getIntExtra("price", 0), intent.getStringExtra("category").toString()))
        finish()
    }
}