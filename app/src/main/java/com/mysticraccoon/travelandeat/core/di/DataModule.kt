package com.mysticraccoon.travelandeat.core.di

import androidx.room.Room
import com.mysticraccoon.travelandeat.core.TravelAndEatConstants
import com.mysticraccoon.travelandeat.core.network.ITravelAndEatAPI
import com.mysticraccoon.travelandeat.core.network.createRetrofitService
import com.mysticraccoon.travelandeat.core.room.TravelAndEatDatabase
import org.koin.dsl.module

val dataModule = module {


    single {
        createRetrofitService().create(ITravelAndEatAPI::class.java)
    }

    single {
        get<TravelAndEatDatabase>().savedPlacesDao()
    }

    single {
        Room.databaseBuilder(get(), TravelAndEatDatabase::class.java, TravelAndEatConstants.databaseName)
            .fallbackToDestructiveMigration()
            .build()
    }



}