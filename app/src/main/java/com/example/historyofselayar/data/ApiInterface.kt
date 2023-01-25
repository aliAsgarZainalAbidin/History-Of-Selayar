package com.example.historyofselayar.data

import android.util.Log
import com.example.historyofselayar.data.model.DetailWisata
import com.example.historyofselayar.data.model.Wisata
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface  {

    @GET("all-wisata")
    suspend fun getAllWisata() : Response<List<Wisata>>

    @GET("detail-wisata/{id}")
    suspend fun getDetailWisata(
        @Path("id")
        id : Int = -1
    ) : Response<DetailWisata>

    @GET("search/{nama}")
    suspend fun search(
        @Path("nama")
        nama : String = ""
    ) : Response<List<Wisata>>
    
    companion object {
        fun create(): ApiInterface {
//            val baseUrl  = "http://10.0.2.2:8000/"
//            val baseUrl  = "http://127.0.0.1:8000/"
            val baseUrl  = "https://api.deval.fun/"
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