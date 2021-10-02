package com.mysticraccoon.travelandeat.core.di

import androidx.room.Room
import com.mysticraccoon.travelandeat.core.utils.TravelAndEatConstants
import com.mysticraccoon.travelandeat.core.network.ITravelAndEatAPI
import com.mysticraccoon.travelandeat.core.network.createRetrofitService
import com.mysticraccoon.travelandeat.core.room.TravelAndEatDatabase
import com.mysticraccoon.travelandeat.data.repository.FoodRepository
import com.mysticraccoon.travelandeat.data.repository.IFoodRepository
import com.mysticraccoon.travelandeat.data.repository.ISavedPlaceRepository
import com.mysticraccoon.travelandeat.data.repository.SavePlaceRepository
import org.koin.dsl.module

val dataModule = module {

    single { SavePlaceRepository(get()) }

    single { FoodRepository(get(), get()) }

    single {
        createRetrofitService().create(ITravelAndEatAPI::class.java)
    }

    single {
        get<TravelAndEatDatabase>().savedPlacesDao()
    }

    single {
        get<TravelAndEatDatabase>().foodCategoryDao()
    }

    single {
        Room.databaseBuilder(get(), TravelAndEatDatabase::class.java, TravelAndEatConstants.databaseName)
            .fallbackToDestructiveMigration()
            .build()
    }







}