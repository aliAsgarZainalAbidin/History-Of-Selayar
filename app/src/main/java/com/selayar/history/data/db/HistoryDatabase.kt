package com.selayar.history.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.selayar.history.data.model.Attach
import com.selayar.history.data.model.DetailWisata
import com.selayar.history.data.model.Wisata

@Database(entities = [DetailWisata::class, Wisata::class, Attach::class], version = 1, exportSchema = true)
@TypeConverters(value = [HistoryConverter::class])
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun wisataDAO() : WisataDAO

    companion object {

        @Volatile
        private var INSTANCE: HistoryDatabase? = null

        fun getDatabase(context: Context): HistoryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(context, HistoryDatabase::class.java, "history.db").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}