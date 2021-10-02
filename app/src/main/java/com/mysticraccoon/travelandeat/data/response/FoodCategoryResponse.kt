package com.mysticraccoon.travelandeat.data.response

import com.mysticraccoon.travelandeat.data.FoodCategory
import com.squareup.moshi.Json

data class FoodCategoryResponse(
    @Json(name = "idCategory")
    val id: String = "",
    @Json(name = "strCategory")
    val title: String? = "",
    @Json(name = "strCategoryThumb")
    val url: String? = ""
){

    fun toFoodCategory(): FoodCategory{
        return FoodCategory(
            id = id,
            title = title.orEmpty(),
            url = url.orEmpty()
        )
    }

}