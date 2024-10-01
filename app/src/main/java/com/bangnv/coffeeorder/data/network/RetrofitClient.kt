package com.bangnv.coffeeorder.data.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object  RetrofitClient {
    private const val BASE_URL = "https://dummyjson.com/c/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClientProvider.client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    val instance: AppApi = retrofit.create(AppApi::class.java)
}