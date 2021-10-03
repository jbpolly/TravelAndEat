package com.mysticraccoon.travelandeat.ui.addEditMeal

import android.app.Application
import android.text.Editable
import android.text.TextWatcher
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
import com.mysticraccoon.travelandeat.ui.base.BaseViewModel
import com.mysticraccoon.travelandeat.ui.base.SingleLiveEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AddEditMealViewModel(private val app: Application, private val foodRepository: FoodRepository): BaseViewModel(app) {

    val mealName = MutableLiveData<String>()
    val mealPriceText = MutableLiveData<String>()
//    val mealPriceDouble = Transformations.map(mealPriceText){ text ->
//        text.toDoubleOrNullFromUS()
//    }
    val mealLocation = MutableLiveData<String>()
    val searchFoodList = MutableLiveData<List<FoodItem>>()
    val cleanSearch = SingleLiveEvent<Boolean>()

    var savedPlace: SavedPlace = SavedPlace()

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
                if (searchText.isNotBlank()) searchMeals(searchText) else cleanSearch.value = true

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



//    private var searchJob: Job? = null
//
//    fun searchDebounced(searchText: String) {
//        searchJob?.cancel()
//        searchJob = viewModelScope.launch {
//            delay(500)
//            searchMeals(searchText)
//        }
//    }

     suspend fun searchMeals(text: String){
         when(val result = foodRepository.searchFoodFromText(text)){
             is NetworkResponse.Success -> {
                 searchFoodList.value = result.body.list.map { it.toFoodItem() }
             }
             is NetworkResponse.ServerError -> {
                 showSnackBar.value = app.getString(R.string.error_service)
             }
             is NetworkResponse.NetworkError -> {
                 showSnackBar.value = app.getString(R.string.error_network)
             }
             else -> {
                 showSnackBar.value = app.getString(R.string.error_unknown)
             }
         }
     }

//
//    fun onClear() {
//        reminderTitle.value = ""
//        reminderDescription.value = ""
//        reminderSelectedLocationStr.value = ""
//        selectedPOI.value = null
//        latitude.value = null
//        longitude.value = null
//    }


//    fun validateAndSaveReminder(savedPlace: SavedPlace) {
//        if (validateEnteredData(reminderData)) {
//            saveSavedPlace(reminderData)
//        }
//    }

    fun saveSavedPlace(savedPlace: SavedPlace) {
        showLoading.value = true
        viewModelScope.launch {
//            dataSource.saveReminder(
//                ReminderDTO(
//                    reminderData.title,
//                    reminderData.description,
//                    reminderData.location,
//                    reminderData.latitude,
//                    reminderData.longitude,
//                    reminderData.id
//                )
//            )
            showLoading.value = false
        }
    }

    /**
     * Validate the entered data and show error to the user if there's any invalid data
     */
//    fun validateEnteredData(savedPlace: SavedPlace): Boolean {
//
//        if (savedPlace.dishName.isEmpty()) {
//            showSnackBar.value = app.getString(R.string.err_enter_name)
//            return false
//        }
//
//        if (savedPlace.location.isNullOrEmpty()) {
//            showSnackBar.value = app.getString(R.string.err_select_location)
//            return false
//        }
//        return true
//    }

    fun setMealLocation(latLong: LatLng, title: String) {
        mealLocation.value = title
        savedPlace.longitude = latLong.longitude
        savedPlace.latitude = latLong.latitude
        savedPlace.location = title
    }

    fun setMeal(foodItem: FoodItem) {
        mealName.value = foodItem.name
        savedPlace.dishName = foodItem.name
        savedPlace.dishCategory = foodItem.category
        savedPlace.dishId = foodItem.id
        savedPlace.dishThumb = foodItem.url
    }


}