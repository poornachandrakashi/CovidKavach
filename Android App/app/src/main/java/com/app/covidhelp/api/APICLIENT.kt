package com.app.covidhelp.api

import okhttp3.OkHttpClient

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object APICLIENT {

    var authToken: String? = null




    val okHttpBuilder = OkHttpClient.Builder()
        .readTimeout(5, TimeUnit.SECONDS)
        .connectTimeout(2, TimeUnit.SECONDS)

    val retrofitBuilder = Retrofit.Builder()
        .baseUrl("http://192.168.29.1")
        .addConverterFactory(MoshiConverterFactory.create())

    val publicApi = retrofitBuilder
        .client(okHttpBuilder.build())
        .build()
        .create(API::class.java)


}