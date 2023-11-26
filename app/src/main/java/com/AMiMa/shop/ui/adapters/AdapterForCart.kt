package com.AMiMa.shop.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.AMiMa.shop.R
import com.AMiMa.shop.database.dataClasses.Product

class AdapterForCart(private val data: List<Product>) : RecyclerView.Adapter<AdapterForCart.MyViewHolders>() {

    class MyViewHolders(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.cartNameProduct)
        val price: TextView = itemView.findViewById(R.id.cartPriceProduct)
        val count: TextView = itemView.findViewById(R.id.cartCountProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolders {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return MyViewHolders(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolders, position: Int) {
        holder.name.text = data[position].name
        holder.price.text = (data[position].price * data[position].count).toString()
        holder.count.text = data[position].count.toString()
    }

    override fun getItemCount() = data.size
}