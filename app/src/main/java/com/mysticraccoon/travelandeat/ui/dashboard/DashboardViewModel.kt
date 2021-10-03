package com.mysticraccoon.travelandeat.ui.dashboard

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.mysticraccoon.travelandeat.R
import com.mysticraccoon.travelandeat.data.ErrorType
import com.mysticraccoon.travelandeat.data.SavedPlace
import com.mysticraccoon.travelandeat.data.repository.SavePlaceRepository
import com.mysticraccoon.travelandeat.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardViewModel(private val app: Application, private val savedPlaceRepository: SavePlaceRepository): BaseViewModel(app) {

    val savedPlacesList = savedPlaceRepository.savedPlaces

    fun updateSavedPlace(savedPlace: SavedPlace){
        viewModelScope.launch {
            showLoading.value = true
            withContext(Dispatchers.IO){
                savedPlaceRepository.updatePlace(savedPlace)
            }
            showLoading.value = false
        }
    }


}