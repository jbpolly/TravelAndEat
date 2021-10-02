package com.mysticraccoon.travelandeat.core.network

import com.mysticraccoon.travelandeat.core.TravelAndEatConstants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit



fun createRetrofitService(): Retrofit {

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val clientBuilder = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)


    val client = clientBuilder.build()

    return Retrofit.Builder()
        .baseUrl(TravelAndEatConstants.base_api_url)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

}