package com.mysticraccoon.travelandeat.data

data class FoodItem(
    val id: String,
    val name: String,
    val url: String,
    val category: String,
    val area: String
){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FoodItem

        if (id != other.id) return false
        if (name != other.name) return false
        if (url != other.url) return false
        if (category != other.category) return false
        if (area != other.area) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + category.hashCode()
        result = 31 * result + area.hashCode()
        return result
    }
}
