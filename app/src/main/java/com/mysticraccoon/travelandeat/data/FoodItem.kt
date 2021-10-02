package com.mysticraccoon.travelandeat.data

import com.squareup.moshi.Json

data class FoodItem(
    val id: String,
    val name: String,
    val url: String,
    val category: String,
    val area: String
)
