package com.mysticraccoon.travelandeat.data.repository

import androidx.lifecycle.LiveData
import com.mysticraccoon.travelandeat.data.ErrorObject
import com.mysticraccoon.travelandeat.data.FoodCategory

interface IFoodRepository {

    val foodCategories: LiveData<List<FoodCategory>>

    suspend fun getFoodCategoryList(): ErrorObject


}