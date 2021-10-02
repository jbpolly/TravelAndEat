package com.mysticraccoon.travelandeat.data.repository

import com.mysticraccoon.travelandeat.data.FoodCategory

interface IFoodRepository {

    fun getFoodCategories(): List<FoodCategory>



}