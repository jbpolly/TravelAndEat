package com.mysticraccoon.travelandeat.core.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mysticraccoon.travelandeat.data.FoodCategory
import com.mysticraccoon.travelandeat.data.SavedPlace


@Database(entities = [SavedPlace::class, FoodCategory::class], version = 1, exportSchema = false)
abstract class TravelAndEatDatabase: RoomDatabase() {

    abstract fun savedPlacesDao(): SavedPlacesDao
    abstract fun foodCategoryDao(): FoodCategoryDao

}