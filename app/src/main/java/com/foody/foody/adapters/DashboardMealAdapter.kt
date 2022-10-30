package com.foody.foody.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foody.foody.databinding.ItemRecyclerDashboardFragmentBinding
import com.foody.foody.model.Meal

class DashboardMealAdapter(val context: Context) :
    ListAdapter<Meal, DashboardMealAdapter.DashboardMealViewHolder>
        (DifCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardMealViewHolder {
        val binding =
            ItemRecyclerDashboardFragmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return DashboardMealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DashboardMealViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class DashboardMealViewHolder(private val binding: ItemRecyclerDashboardFragmentBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
//                listener.onItemClick(position)
            }
        }


        fun bind(meal: Meal) {
            binding.apply {
                idItemRecyclerDashboardFragment.text = meal.idMeal
                titleItemRecyclerDashboardFragment.text = meal.strMeal
                Glide.with(imageItemRecyclerDashboardFragment)
                    .load(meal.strMealThumb)
                    .into(imageItemRecyclerDashboardFragment)
            }
        }
    }

    class DifCallback : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal) =
            oldItem.idMeal == newItem.idMeal

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal) = oldItem == newItem

    }
}