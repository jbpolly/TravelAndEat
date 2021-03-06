package com.mysticraccoon.travelandeat.ui.explore

import android.app.Application
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.mysticraccoon.travelandeat.R
import com.mysticraccoon.travelandeat.data.ErrorType
import com.mysticraccoon.travelandeat.data.repository.FoodRepository
import com.mysticraccoon.travelandeat.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExploreViewModel(private val app: Application, private val foodRepository: FoodRepository): BaseViewModel(app) {

    val foodCategories = foodRepository.foodCategories
    val isListEmpty = Transformations.map(foodCategories){ list ->
        if(list.isNullOrEmpty()){
            return@map true
        }
        false
    }

    init {
        refreshFoodCategories()
    }

    fun refreshFoodCategories(){
        viewModelScope.launch {
            showLoading.value = true
            val result = foodRepository.getFoodCategoryList()
            if(result.isError){
                when(result.errorType){
                    ErrorType.NETWORK -> showSnackBar.value = app.getString(R.string.error_network)
                    ErrorType.SERVICE -> showSnackBar.value = app.getString(R.string.error_service)
                    else -> showSnackBar.value = app.getString(R.string.error_unknown)
                }
            }
            showLoading.value = false
        }
    }

}