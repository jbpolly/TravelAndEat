package com.mysticraccoon.travelandeat.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mysticraccoon.travelandeat.data.SavedPlace
import com.mysticraccoon.travelandeat.databinding.ViewholderPlaceListItemBinding

class SavedPlaceAdapter(private val onItemClicked: SavedPlaceClicked, private val onCheckedChanged: SavedPlaceCheckChanged) :
    ListAdapter<SavedPlace, SavedPlaceAdapter.SavedPlaceViewHolder>(
        SavedPlaceDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedPlaceViewHolder {
        return SavedPlaceViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SavedPlaceViewHolder, position: Int) {
        val categoryItem = getItem(position)
        holder.bind(categoryItem, onItemClicked, onCheckedChanged)
    }

    class SavedPlaceViewHolder(private val foodCategoryItemBinding: ViewholderPlaceListItemBinding) :
        RecyclerView.ViewHolder(foodCategoryItemBinding.root) {

        fun bind(item: SavedPlace, itemClick: SavedPlaceClicked, checkChanged: SavedPlaceCheckChanged) {

            foodCategoryItemBinding.dishName.text = item.dishName
            foodCategoryItemBinding.locationTitle.text = item.location
            val valueText = "$${item.dishValue}"
            foodCategoryItemBinding.dishValue.text = valueText
            foodCategoryItemBinding.visitedCheck.isChecked = item.checked
            foodCategoryItemBinding.visitedLine.visibility = if(item.checked) View.VISIBLE else View.GONE

            foodCategoryItemBinding.visitedCheck.setOnCheckedChangeListener { _, checked ->
                item.checked = checked
                foodCategoryItemBinding.visitedLine.visibility = if(checked) View.VISIBLE else View.GONE
                checkChanged.onClick(checked, item)
            }

            itemView.setOnClickListener {
                itemClick.onClick(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup): SavedPlaceViewHolder {
                val binding = ViewholderPlaceListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return SavedPlaceViewHolder(binding)
            }
        }
    }

}


class SavedPlaceClicked(val block: (SavedPlace) -> Unit) {
    fun onClick(category: SavedPlace) = block(category)
}

class SavedPlaceCheckChanged(val block: (Boolean, SavedPlace) -> Unit) {
    fun onClick(isChecked: Boolean, savedPlace: SavedPlace) = block(isChecked, savedPlace)
}

class SavedPlaceDiffCallback : DiffUtil.ItemCallback<SavedPlace>() {

    override fun areItemsTheSame(oldItem: SavedPlace, newItem: SavedPlace): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SavedPlace, newItem: SavedPlace): Boolean {
        return oldItem == newItem
    }
}