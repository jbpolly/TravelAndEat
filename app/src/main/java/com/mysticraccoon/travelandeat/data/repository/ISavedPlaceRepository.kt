package com.mysticraccoon.travelandeat.data.repository

import androidx.lifecycle.LiveData
import com.mysticraccoon.travelandeat.data.SavedPlace

interface ISavedPlaceRepository {

    val savedPlaces: LiveData<List<SavedPlace>>
    suspend fun getSavedPlaceById(id: String): SavedPlace?
    suspend fun deleteSavedPlaceById(id: String)
    suspend fun savePlace(place: SavedPlace)
    suspend fun updatePlace(place: SavedPlace)
    suspend fun deleteAllSavedPlaces()

}