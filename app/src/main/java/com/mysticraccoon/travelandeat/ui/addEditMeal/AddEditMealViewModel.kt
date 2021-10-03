package com.mysticraccoon.travelandeat.ui.addEditMeal

import android.app.Application
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.haroldadmin.cnradapter.NetworkResponse
import com.mysticraccoon.travelandeat.R
import com.mysticraccoon.travelandeat.core.utils.debounce
import com.mysticraccoon.travelandeat.core.utils.toDoubleOrNullFromUS
import com.mysticraccoon.travelandeat.data.FoodItem
import com.mysticraccoon.travelandeat.data.SavedPlace
import com.mysticraccoon.travelandeat.data.repository.FoodRepository
import com.mysticraccoon.travelandeat.data.repository.SavePlaceRepository
import com.mysticraccoon.travelandeat.ui.base.BaseViewModel
import com.mysticraccoon.travelandeat.ui.base.SingleLiveEvent
import kotlinx.coroutines.*

class AddEditMealViewModel(
    private val app: Application,
    private val foodRepository: FoodRepository,
    private val savedPlacesRepository: SavePlaceRepository
) : BaseViewModel(app) {

    val mealName = MutableLiveData<String>()
    val mealPriceText = MutableLiveData<String>()

    //    val mealPriceDouble = Transformations.map(mealPriceText){ text ->
//        text.toDoubleOrNullFromUS()
//    }
    val mealLocation = MutableLiveData<String>()
    val searchFoodList = MutableLiveData<List<FoodItem>>()
    val cleanSearch = SingleLiveEvent<Boolean>()
    val deletedComplete = SingleLiveEvent<Boolean>()
    val saveComplete = SingleLiveEvent<Boolean>()


    var savedPlace: SavedPlace? = null

    val isSearchEmpty = MutableLiveData(true)
    val searchDebounceWatcher = object : TextWatcher {
        private var searchFor = ""

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val searchText = s.toString().trim()
            if (searchText == searchFor)
                return

            searchFor = searchText

            viewModelScope.launch {
                delay(300)  //debounce timeOut
                if (searchText != searchFor)
                    return@launch
                if (searchText.isNotBlank()) {
                    searchMeals(searchText)
                } else {
                    cleanSearch.value = true
                    isSearchEmpty.value = true
                }

            }
        }

        override fun afterTextChanged(s: Editable?) = Unit
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
    }

    fun loadSavedPlace(place: SavedPlace?) {
        place?.let {
            savedPlace = it
            mealName.value = it.dishName
            mealPriceText.value = it.dishValue
            mealLocation.value = it.location
        }
    }

    fun createSavedPlace() {
        savedPlace = SavedPlace()
    }

    fun clearSavedPlace(){
        savedPlace = null
        mealName.value = ""
        mealPriceText.value = ""
        mealLocation.value = ""
        searchFoodList.value = listOf()
    }


    suspend fun searchMeals(text: String) {
        showLoading.value = true
        when (val result = foodRepository.searchFoodFromText(text)) {
            is NetworkResponse.Success -> {
                result.body.list?.let {  list ->
                    searchFoodList.value = list.map { it.toFoodItem() }
                    isSearchEmpty.value = false
                } ?: run {
                    isSearchEmpty.value = true
                }

            }
            is NetworkResponse.ServerError -> {
                showSnackBar.value = app.getString(R.string.error_service)
                isSearchEmpty.value = true
            }
            is NetworkResponse.NetworkError -> {
                showSnackBar.value = app.getString(R.string.error_network)
                isSearchEmpty.value = true
            }
            else -> {
                showSnackBar.value = app.getString(R.string.error_unknown)
                isSearchEmpty.value = true
            }
        }
        showLoading.value = false
    }

    fun saveSavedPlace() {
        savedPlace?.let { place ->
            place.dishValue = mealPriceText.value ?: "0.0"
            viewModelScope.launch {
                showLoading.value = true
                withContext(Dispatchers.IO) {
                    savedPlacesRepository.savePlace(place)
                }
                saveComplete.value = true
                showLoading.value = false
            }
        }
    }

    fun validateEnteredData(): Boolean {
        savedPlace?.let { place ->
            if (place.dishName.isEmpty()) {
                showSnackBar.value = app.getString(R.string.err_enter_name)
                return false
            }

            if (place.location.isEmpty()) {
                showSnackBar.value = app.getString(R.string.err_select_location)
                return false
            }
            return true
        } ?: run {
            showSnackBar.value = app.getString(R.string.error_null_data)
            return false
        }
    }

    fun setMealLocation(latLong: LatLng, title: String) {
        savedPlace?.let { savedPlace ->
            mealLocation.value = title
            savedPlace.longitude = latLong.longitude
            savedPlace.latitude = latLong.latitude
            savedPlace.location = title
        }
    }

    fun setMeal(foodItem: FoodItem) {
        savedPlace?.let { place ->
            mealName.value = foodItem.name
            place.dishName = foodItem.name
            place.dishCategory = foodItem.category
            place.dishId = foodItem.id
            place.dishThumb = foodItem.url
        }
    }

    fun deleteSavedPlace() {
        savedPlace?.let { place ->
            viewModelScope.launch {
                showLoading.value = true
                withContext(Dispatchers.IO) {
                    savedPlacesRepository.deleteSavedPlaceById(place.id)
                }
                deletedComplete.value = true
                showLoading.value = false
            }
        } ?: run {
            showSnackBar.value = app.getString(R.string.error_null_data)
        }

    }


}