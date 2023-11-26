package com.AMiMa.shop.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.AMiMa.shop.R
import com.AMiMa.shop.model.WorkInCart
import com.AMiMa.shop.ui.adapters.AdapterForCart

class Cart : AppCompatActivity() {

    private lateinit var totalPriceProducts: TextView
    private lateinit var buyProducts: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val workInCart = WorkInCart.getInstance()
        val listProducts: RecyclerView = findViewById(R.id.cartListProducts)
        listProducts.apply {
            layoutManager = LinearLayoutManager(this@Cart)
            adapter = AdapterForCart(workInCart.getListProducts())
        }

        totalPriceProducts = findViewById(R.id.cartTotalPrice)
        totalPriceProducts.text = workInCart.getTotalPrice().toString()
    }
}