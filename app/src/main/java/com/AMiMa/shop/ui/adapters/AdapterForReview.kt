package com.AMiMa.shop.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.AMiMa.shop.R
import com.AMiMa.shop.database.dataClasses.Review

class AdapterForReview(private val data: List<Review>) : RecyclerView.Adapter<AdapterForReview.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val author: TextView = itemView.findViewById(R.id.author)
        val review: TextView = itemView.findViewById(R.id.review)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.author.text = data[position].author
        holder.review.text = data[position].review
    }

    override fun getItemCount() = data.size
}