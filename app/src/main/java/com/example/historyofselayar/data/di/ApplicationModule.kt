package com.example.historyofselayar.data.di

import android.content.Context
import androidx.room.Room
import com.example.historyofselayar.data.ApiInterface
import com.example.historyofselayar.utils.HistoryApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Provides
    fun provideApplication(@ApplicationContext context: Context): HistoryApplication =
        context as HistoryApplication

    @Provides
    fun provideAPIService(@ApplicationContext context: Context,): ApiInterface {
        return ApiInterface.create()
    }

}