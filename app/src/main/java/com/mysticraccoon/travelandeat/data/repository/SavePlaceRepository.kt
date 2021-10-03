package com.mysticraccoon.travelandeat.data.repository

import androidx.lifecycle.LiveData
import com.mysticraccoon.travelandeat.core.room.SavedPlacesDao
import com.mysticraccoon.travelandeat.data.FoodCategory
import com.mysticraccoon.travelandeat.data.SavedPlace

class SavePlaceRepository(private val savedPlacesDao: SavedPlacesDao): ISavedPlaceRepository {

    override val savedPlaces: LiveData<List<SavedPlace>> = savedPlacesDao.getSavedPlaces()

    override suspend fun getSavedPlaceById(id: String): SavedPlace?{
        return savedPlacesDao.getSavedPlaceById(id)
    }

    override suspend fun savePlace(place: SavedPlace) {
        savedPlacesDao.savePlace(place)
    }

    override suspend fun updatePlace(place: SavedPlace) {
        savedPlacesDao.updatePlace(place)
    }

    override suspend fun deleteSavedPlaceById(id: String) {
        savedPlacesDao.deleteSavedPlaceById(id)
    }

    override suspend fun deleteAllSavedPlaces() {
        savedPlacesDao.deleteAllSavedPlaces()
    }
}