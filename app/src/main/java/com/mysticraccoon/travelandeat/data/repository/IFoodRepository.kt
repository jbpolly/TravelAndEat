package com.mysticraccoon.travelandeat.data.repository

import androidx.lifecycle.LiveData
import com.haroldadmin.cnradapter.NetworkResponse
import com.mysticraccoon.travelandeat.data.ErrorObject
import com.mysticraccoon.travelandeat.data.FoodCategory
import com.mysticraccoon.travelandeat.data.response.FoodItemListResponse

interface IFoodRepository {

    val foodCategories: LiveData<List<FoodCategory>>
    suspend fun getFoodCategoryList(): ErrorObject
    suspend fun getFoodsFromCategory(category: String): NetworkResponse<FoodItemListResponse, String>
    suspend fun searchFoodFromText(text: String): NetworkResponse<FoodItemListResponse, String>

}