package com.logic.newsbreeze.savedList.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.logic.newsbreeze.R
import com.logic.newsbreeze.loadImage
import com.logic.newsbreeze.main.model.ArticlesItem
import com.logic.newsbreeze.monthFromDate
import java.util.*

class ItemListAdapter(val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        LayoutInflater.from(context)
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_saved_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            val data = articleList[position]
            if (!data.urlToImage.isNullOrEmpty()) {
                holder.coverImage.loadImage(data.urlToImage)
            }
            holder.title.text = data.title
            holder.tag.text = "#Sample"
            holder.time.text = monthFromDate(data.publishedAt!!)

        }
    }


    private val articleList = ArrayList<ArticlesItem>()

    override fun getItemCount(): Int = articleList.size

    fun addData(list: List<ArticlesItem>) {
        articleList.clear()
        articleList.addAll(list)
        notifyDataSetChanged()
    }


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val coverImage: ImageView = view.findViewById(R.id.imv_cover)
        val title: TextView = view.findViewById(R.id.tv_title)
        var time: TextView = view.findViewById(R.id.tv_time_author)
        val tag: TextView = view.findViewById(R.id.tv_tag)
    }


}