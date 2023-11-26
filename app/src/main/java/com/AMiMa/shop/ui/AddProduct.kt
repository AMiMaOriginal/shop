package com.AMiMa.shop.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import com.AMiMa.shop.R
import com.AMiMa.shop.database.Database
import com.AMiMa.shop.database.dao.ProductDao
import com.AMiMa.shop.database.dataClasses.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AddProduct : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var nameProductView: EditText
    private lateinit var categoryProductView: Spinner
    private lateinit var priceProductView: EditText
    private lateinit var addProductView: Button
    private lateinit var addErrorView: TextView
    private lateinit var database: ProductDao
    private lateinit var nameProduct: String
    private lateinit var categoryProduct: String
    private lateinit var priceProduct: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        database = Database.getDatabase(this)!!.productDao()
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.categories))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        nameProductView = findViewById(R.id.addNameProduct)
        categoryProductView = findViewById(R.id.addCategoryProduct)
        categoryProductView.apply {
            onItemSelectedListener = this@AddProduct
            setAdapter(adapter)
        }
        priceProductView = findViewById(R.id.addPriceProduct)
        priceProductView.apply {
            setOnKeyListener(View.OnKeyListener { view, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                    addProduct()
                    return@OnKeyListener true
                }
                false
            })
        }
        addProductView = findViewById(R.id.addProduct)
        addProductView.setOnClickListener { addProduct() }
        addErrorView = findViewById(R.id.addProductError)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        categoryProduct = parent?.selectedItem.toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        categoryProduct = resources.getStringArray(R.array.categories)[0]
    }

    private fun initInputText(){
        nameProduct = nameProductView.text.toString()
        priceProduct = priceProductView.text.toString()
    }

    private fun addProduct() = runBlocking{
        initInputText()

        launch(Dispatchers.IO) {
            if (nameProduct.trim().isNullOrEmpty() || priceProduct.trim().isNullOrEmpty())
                runOnUiThread { addErrorView.text = "Поля не должны быть пустыми" }
            else if (database.getProduct(nameProduct, priceProduct, categoryProduct) != null)
                runOnUiThread { addErrorView.text = "Такой товар уже существует" }
            else {
                database.addProduct(Product(nameProduct, priceProduct.toInt(), categoryProduct))
                finish()
            }
        }

    }
}