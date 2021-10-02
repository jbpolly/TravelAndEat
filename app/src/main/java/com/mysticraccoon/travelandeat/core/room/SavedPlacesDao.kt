package com.mysticraccoon.travelandeat.core.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mysticraccoon.travelandeat.core.TravelAndEatConstants.savedPlacesIdColumnName
import com.mysticraccoon.travelandeat.core.TravelAndEatConstants.savedPlacesTableName
import com.mysticraccoon.travelandeat.data.SavedPlace

@Dao
interface SavedPlacesDao {

    @Query("SELECT * FROM $savedPlacesTableName")
    fun getSavedPlaces(): LiveData<SavedPlace>

    @Query("SELECT * FROM $savedPlacesTableName where $savedPlacesIdColumnName = :savedPlaceId")
    suspend fun getSavedPlaceById(savedPlaceId: String): SavedPlace?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePlace(place: SavedPlace)

    @Delete
    suspend fun deleteSavedPlace(savedPlace: SavedPlace)

}