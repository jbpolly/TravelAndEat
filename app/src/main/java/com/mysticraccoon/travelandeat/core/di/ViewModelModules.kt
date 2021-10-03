package com.mysticraccoon.travelandeat.core.di

import com.mysticraccoon.travelandeat.ui.addEditMeal.AddEditMealViewModel
import com.mysticraccoon.travelandeat.ui.dashboard.DashboardViewModel
import com.mysticraccoon.travelandeat.ui.explore.ExploreViewModel
import com.mysticraccoon.travelandeat.ui.foodFromCategory.FoodFromCategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { DashboardViewModel(get(), get()) }
    viewModel { ExploreViewModel(get(), get()) }
    viewModel { FoodFromCategoryViewModel(get(), get()) }
    viewModel { AddEditMealViewModel(get(), get()) }

}