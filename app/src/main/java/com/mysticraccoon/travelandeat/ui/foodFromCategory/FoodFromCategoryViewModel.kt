package com.mysticraccoon.travelandeat.ui.foodFromCategory

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.mysticraccoon.travelandeat.R
import com.mysticraccoon.travelandeat.data.FoodCategory
import com.mysticraccoon.travelandeat.data.FoodItem
import com.mysticraccoon.travelandeat.data.repository.FoodRepository
import com.mysticraccoon.travelandeat.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

class FoodFromCategoryViewModel(private val app: Application, private val foodRepository: FoodRepository): BaseViewModel(app) {

    val categoryText = MutableLiveData("")
    val foodList = MutableLiveData<List<FoodItem>>()


    fun setCategory(category: String) {
        categoryText.value = category
        viewModelScope.launch {

            when(val listResult = foodRepository.getFoodsFromCategory(category)){
                is NetworkResponse.Success -> {
                    foodList.value = listResult.body.list.map { it.toFoodItem() }
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

    }


}