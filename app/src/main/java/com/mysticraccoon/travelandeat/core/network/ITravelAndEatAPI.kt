package com.mysticraccoon.travelandeat.core.network

import com.haroldadmin.cnradapter.NetworkResponse
import com.mysticraccoon.travelandeat.data.response.FoodCategoryListResponse
import com.mysticraccoon.travelandeat.data.response.FoodItemListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ITravelAndEatAPI {

    @GET("categories.php")
    suspend fun getFoodCategories(): NetworkResponse<FoodCategoryListResponse, String>

    @GET("search.php")
    suspend fun searchFoods(@Query("s")text: String): NetworkResponse<FoodItemListResponse, String>



}