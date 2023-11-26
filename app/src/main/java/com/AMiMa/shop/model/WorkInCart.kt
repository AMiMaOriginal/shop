package com.AMiMa.shop.model

import com.AMiMa.shop.database.Database
import com.AMiMa.shop.database.dataClasses.Product

class WorkInCart private constructor() {

    private var listProduct = mutableListOf<Product>()

    fun addInListProducts(element: Product){
        var found = false
        listProduct.forEach{
            if (it.equals(element)){
                it.count += element.count
                found = true
            }
        }
        if (!found)
            listProduct.add(element)
    }

    fun getListProducts() = listProduct

    fun getTotalPrice() : Int {
        var totalPrice = 0
        listProduct.forEach{ totalPrice += it.price * it.count }
        return totalPrice
    }

    companion object{
        private var instance: WorkInCart? = null

        fun getInstance(): WorkInCart {
            synchronized(this) {
                if (instance == null)
                    instance = WorkInCart()
            }
            return instance!!
        }
    }
}