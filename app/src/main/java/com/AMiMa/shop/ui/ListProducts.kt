package com.AMiMa.shop.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.AMiMa.shop.R
import com.AMiMa.shop.database.Database
import com.AMiMa.shop.database.Parser
import com.AMiMa.shop.database.dao.ProductDao
import com.AMiMa.shop.database.dataClasses.Product
import com.AMiMa.shop.ui.adapters.AdapterForProducts
import org.jsoup.Jsoup
import java.net.URL
import kotlin.concurrent.thread

class ListProducts : AppCompatActivity() {

    private lateinit var layout: LinearLayout
    private lateinit var listProduct: RecyclerView
    private lateinit var data: List<Product>
    private lateinit var database: ProductDao
    private lateinit var adapter: AdapterForProducts
    private var admin = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_products)

        //database = Database.getDatabase(this)!!.productDao()
        initProducts()

        layout = findViewById(R.id.listProductsActivity)
        listProduct = findViewById(R.id.listProducts)
        listProduct.apply {
            layoutManager = LinearLayoutManager(this@ListProducts)
            //adapter = AdapterForProducts(data)
        }

        if (admin){
            val addProduct = Button(this)
            addProduct.apply {
                text = "Добавить"
                setOnClickListener { startActivity(Intent(this@ListProducts, AddProduct::class.java)) }
            }
            layout.addView(addProduct)
        }
    }

    private fun initProducts() {
        val parser = Parser()
        parser.connect()
    }

    fun getDetailedProduct(view: View){
        var intent = Intent(this, DetailedProduct::class.java)
        val product = data[view.id]
        intent.putExtra("name", product.name)
        intent.putExtra("price", product.price)
        intent.putExtra("category", product.category)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.drawer_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.personalCabinet -> startActivity(Intent(this, PersonalCabinet::class.java))
            R.id.cart -> startActivity(Intent(this, Cart::class.java))
            R.id.log -> startActivity(Intent(this, RegisterActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}