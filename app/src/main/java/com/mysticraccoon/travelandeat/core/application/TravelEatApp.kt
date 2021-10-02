package com.mysticraccoon.travelandeat.core.application

import android.app.Application
import com.mysticraccoon.travelandeat.core.di.dataModule
import com.mysticraccoon.travelandeat.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TravelEatApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startDependencyInjection()
    }

    private fun startDependencyInjection() {

        startKoin {
            androidContext(this@TravelEatApp)
            modules(
                listOf(
                    dataModule,
                    viewModelModule,
                )
            )
        }

    }


}