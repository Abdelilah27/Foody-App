package com.foody.foody.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foody.foody.R
import com.foody.foody.model.Meal

class DashboardMealAdapter(val context: Context) :
    RecyclerView.Adapter<DashboardMealAdapter.ItemViewHolder>() {
    private var myList: List<Meal> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Meal>) {
        myList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycler_dashboard_fragment, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        myList[position].let {
            holder.id.text = it.idMeal
            holder.title.text = it.strMeal
            Glide.with(context).load(myList.get(position).strMealThumb).into(holder.image)
        }
    }


    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var id: TextView = view.findViewById(R.id.id_item_recycler_dashboard_fragment)
        var title: TextView = view.findViewById(R.id.title_item_recycler_dashboard_fragment)
        var image: ImageView = view.findViewById(R.id.image_item_recycler_dashboard_fragment)
    }

    override fun getItemCount() = myList.size
}