package com.AMiMa.shop.ui.adapters

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.AMiMa.shop.R
import com.AMiMa.shop.database.dataClasses.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.URL

class AdapterForProducts(private val data: List<Product>) : RecyclerView.Adapter<AdapterForProducts.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.listProductsImage)
        val name: TextView = itemView.findViewById(R.id.listProductsName)
        val price: TextView = itemView.findViewById(R.id.listProductsPrice)
        val product: RelativeLayout = itemView.findViewById(R.id.product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_products, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) = runBlocking{
        launch(Dispatchers.IO) {
            val url = URL(data[position].imageURL)
            val input = url.openStream()
            holder.image.setImageBitmap(BitmapFactory.decodeStream(input))
        }
        holder.name.text = data[position].description
        holder.price.text = data[position].price.toString()
        holder.product.id = position
    }

    override fun getItemCount() = data.size
}