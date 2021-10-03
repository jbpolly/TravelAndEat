package com.mysticraccoon.travelandeat.data.repository

import androidx.lifecycle.LiveData
import com.haroldadmin.cnradapter.NetworkResponse
import com.mysticraccoon.travelandeat.core.network.ITravelAndEatAPI
import com.mysticraccoon.travelandeat.core.room.FoodCategoryDao
import com.mysticraccoon.travelandeat.data.ErrorObject
import com.mysticraccoon.travelandeat.data.ErrorType
import com.mysticraccoon.travelandeat.data.FoodCategory
import com.mysticraccoon.travelandeat.data.response.FoodItemListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodRepository(
    private val api: ITravelAndEatAPI,
    private val foodCategoryDao: FoodCategoryDao
): IFoodRepository {

    override val foodCategories: LiveData<List<FoodCategory>> = foodCategoryDao.getFoodCategories()

    override suspend fun getFoodCategoryList(): ErrorObject{
        when(val categoriesListResponse = api.getFoodCategories()){
            is NetworkResponse.Success -> {
                val list = categoriesListResponse.body.list.map { it.toFoodCategory() }
                withContext(Dispatchers.IO){
                    foodCategoryDao.deleteCategories()
                    foodCategoryDao.saveFoodCategories(list)
                }
                return ErrorObject(isError = false, errorType = null)
            }
            is NetworkResponse.NetworkError -> {
                return ErrorObject(isError = true, errorType = ErrorType.NETWORK)
            }
            is NetworkResponse.ServerError -> {
                return ErrorObject(isError = true, errorType = ErrorType.SERVICE)
            }
            else -> {
                return ErrorObject(isError = true, errorType = ErrorType.UNKNOWN)
            }
        }
    }

    suspend fun getFoodsFromCategory(category: String): NetworkResponse<FoodItemListResponse, String>{
        return api.getFoodByCategory(category)
    }

    suspend fun searchFoodFromText(text: String): NetworkResponse<FoodItemListResponse, String>{
        return api.searchFoods(text)
    }




}