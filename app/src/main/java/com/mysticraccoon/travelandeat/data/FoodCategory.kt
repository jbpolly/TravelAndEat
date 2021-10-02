package com.mysticraccoon.travelandeat.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mysticraccoon.travelandeat.core.utils.TravelAndEatConstants.foodCategoryTableName

@Entity(tableName = foodCategoryTableName)
data class FoodCategory(
    @PrimaryKey
    val id: String,
    val title: String,
    val url: String
){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FoodCategory

        if (id != other.id) return false
        if (title != other.title) return false
        if (url != other.url) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + url.hashCode()
        return result
    }
}