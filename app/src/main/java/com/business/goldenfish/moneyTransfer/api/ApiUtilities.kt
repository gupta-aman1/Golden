package com.business.goldenfish.moneyTransfer.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiUtilities {
    val BASE_URL = "https://uat.goldenfishdigital.co.in/api/"
    val IMAGE_URL = "https://uat.goldenfishdigital.co.in/"


    var interceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }


    fun getInstance() : Retrofit
    {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS).build()


        return Retrofit.Builder()
            .baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    }
}