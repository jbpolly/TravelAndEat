package com.mysticraccoon.travelandeat.ui.explore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mysticraccoon.travelandeat.data.FoodCategory
import com.mysticraccoon.travelandeat.databinding.ViewholderFoodCategoryListItemBinding

class ExploreCategoriesAdapter(private val onItemClicked: FoodCategoryClicked) :
    ListAdapter<FoodCategory, ExploreCategoriesAdapter.FoodCategoryItemViewHolder>(
        FoodCategoryDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodCategoryItemViewHolder {
        return FoodCategoryItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FoodCategoryItemViewHolder, position: Int) {
        val categoryItem = getItem(position)
        holder.bind(categoryItem, onItemClicked)
    }

    class FoodCategoryItemViewHolder(private val foodCategoryItemBinding: ViewholderFoodCategoryListItemBinding) :
        RecyclerView.ViewHolder(foodCategoryItemBinding.root) {

        fun bind(item: FoodCategory, itemClick: FoodCategoryClicked) {

            foodCategoryItemBinding.itemTitle.text = item.title
            foodCategoryItemBinding.thumbUrl = item.url
            itemView.setOnClickListener {
                itemClick.onClick(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup): FoodCategoryItemViewHolder {
                val binding = ViewholderFoodCategoryListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return FoodCategoryItemViewHolder(binding)
            }
        }

    }

}


class FoodCategoryClicked(val block: (FoodCategory) -> Unit) {
    fun onClick(category: FoodCategory) = block(category)
}

class FoodCategoryDiffCallback : DiffUtil.ItemCallback<FoodCategory>() {

    override fun areItemsTheSame(oldItem: FoodCategory, newItem: FoodCategory): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FoodCategory, newItem: FoodCategory): Boolean {
        return oldItem == newItem
    }
}