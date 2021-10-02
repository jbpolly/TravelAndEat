package com.mysticraccoon.travelandeat.core.di

import com.mysticraccoon.travelandeat.ui.dashboard.DashboardViewModel
import com.mysticraccoon.travelandeat.ui.explore.ExploreViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { DashboardViewModel(get()) }
    viewModel { ExploreViewModel(get(), get()) }

}