package com.mysticraccoon.travelandeat.data.response

import com.mysticraccoon.travelandeat.data.FoodItem
import com.squareup.moshi.Json

data class FoodItemListResponse(
    @Json(name = "meals")
    val list: List<FoodItemResponse>
)

data class FoodItemResponse(
    @Json(name = "idMeal")
    val id: String,
    @Json(name = "strMeal")
    val name: String? = "",
    @Json(name = "strMealThumb")
    val url: String? = "",
    @Json(name = "strCategory")
    val category: String? = "",
    @Json(name = "strArea")
    val area: String? = ""
){

    fun toFoodItem(): FoodItem{
        return FoodItem(
            id = id,
            name = name.orEmpty(),
            url = url.orEmpty(),
            category = category.orEmpty(),
            area = area.orEmpty()
        )
    }

}