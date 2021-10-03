package com.mysticraccoon.travelandeat.data.repository

import androidx.lifecycle.LiveData
import com.mysticraccoon.travelandeat.data.SavedPlace

interface ISavedPlaceRepository {

    fun getSavedPlaces(): LiveData<List<SavedPlace>>
    suspend fun getSavedPlaceById(id: String): SavedPlace?
    suspend fun deleteSavedPlace(savedPlace: SavedPlace)
    suspend fun savePlace(place: SavedPlace)
    suspend fun updatePlace(place: SavedPlace)

}