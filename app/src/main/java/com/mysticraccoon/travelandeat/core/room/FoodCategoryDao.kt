package com.mysticraccoon.travelandeat.core.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mysticraccoon.travelandeat.core.utils.TravelAndEatConstants
import com.mysticraccoon.travelandeat.data.FoodCategory

@Dao
interface FoodCategoryDao {

    @Query("SELECT * FROM ${TravelAndEatConstants.foodCategoryTableName}")
    fun getFoodCategories(): LiveData<List<FoodCategory>>

    @Query("DELETE from ${TravelAndEatConstants.foodCategoryTableName}")
    suspend fun deleteCategories()

    @Insert
    suspend fun saveFoodCategories(list: List<FoodCategory>)

}