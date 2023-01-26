package com.selayar.history.data.di

import android.content.Context
import com.selayar.history.data.ApiInterface
import com.selayar.history.data.db.HistoryDatabase
import com.selayar.history.data.db.WisataDAO
import com.selayar.history.utils.HistoryApplication
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

    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : HistoryDatabase {
        return HistoryDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideWisataDAO(database: HistoryDatabase) : WisataDAO {
        return database.wisataDAO()
    }
}