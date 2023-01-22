package com.example.historyofselayar.data

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface  {
    
    companion object {
        fun create(): ApiInterface {
            val baseUrl  = "http://localhost:8080/"
            val logger = HttpLoggingInterceptor { Log.d("WORX-API", it) }
            logger.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                        .build()
                    chain.proceed(newRequest)
                }
                .addInterceptor(logger)
                .build()

            val gson = GsonBuilder()
                .create()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiInterface::class.java)
        }
    }
}