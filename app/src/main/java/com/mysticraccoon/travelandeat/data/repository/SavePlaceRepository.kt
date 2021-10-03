package com.mysticraccoon.travelandeat.data.repository

import androidx.lifecycle.LiveData
import com.mysticraccoon.travelandeat.core.room.SavedPlacesDao
import com.mysticraccoon.travelandeat.data.SavedPlace

class SavePlaceRepository(private val savedPlacesDao: SavedPlacesDao): ISavedPlaceRepository {

    override fun getSavedPlaces(): LiveData<List<SavedPlace>> {
        return savedPlacesDao.getSavedPlaces()
    }

    override suspend fun getSavedPlaceById(id: String): SavedPlace?{
        return savedPlacesDao.getSavedPlaceById(id)
    }

    override suspend fun deleteSavedPlace(savedPlace: SavedPlace) {
        savedPlacesDao.deleteSavedPlace(savedPlace)
    }

    override suspend fun savePlace(place: SavedPlace) {
        savedPlacesDao.savePlace(place)
    }

    override suspend fun updatePlace(place: SavedPlace) {
        savedPlacesDao.updatePlace(place)
    }
}