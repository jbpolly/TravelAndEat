package com.mysticraccoon.travelandeat.core.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mysticraccoon.travelandeat.core.utils.TravelAndEatConstants.savedPlacesIdColumnName
import com.mysticraccoon.travelandeat.core.utils.TravelAndEatConstants.savedPlacesTableName
import com.mysticraccoon.travelandeat.data.SavedPlace

@Dao
interface SavedPlacesDao {

    @Query("SELECT * FROM $savedPlacesTableName")
    fun getSavedPlaces(): LiveData<List<SavedPlace>>

    @Query("SELECT * FROM $savedPlacesTableName where $savedPlacesIdColumnName = :savedPlaceId")
    suspend fun getSavedPlaceById(savedPlaceId: String): SavedPlace?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePlace(place: SavedPlace)

    @Update
    suspend fun updatePlace(place: SavedPlace)

    @Query("DELETE FROM $savedPlacesTableName WHERE $savedPlacesIdColumnName = :id")
    suspend fun deleteSavedPlaceById(id: String)

}