package com.mysticraccoon.travelandeat.core.di

import androidx.room.Room
import com.mysticraccoon.travelandeat.core.utils.TravelAndEatConstants
import com.mysticraccoon.travelandeat.core.network.ITravelAndEatAPI
import com.mysticraccoon.travelandeat.core.network.createRetrofitService
import com.mysticraccoon.travelandeat.core.room.TravelAndEatDatabase
import com.mysticraccoon.travelandeat.data.repository.ISavedPlaceRepository
import com.mysticraccoon.travelandeat.data.repository.SavePlaceRepository
import org.koin.dsl.module

val dataModule = module {

    single {
        createRetrofitService().create(ITravelAndEatAPI::class.java)
    }

    single {
        Room.databaseBuilder(get(), TravelAndEatDatabase::class.java, TravelAndEatConstants.databaseName)
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<TravelAndEatDatabase>().savedPlacesDao()
    }

    single {
        get<TravelAndEatDatabase>().foodCategoryDao()
    }

    single<ISavedPlaceRepository> { SavePlaceRepository(get()) }



}