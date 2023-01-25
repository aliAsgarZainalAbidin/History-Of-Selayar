package com.example.historyofselayar.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.historyofselayar.data.model.DetailWisata
import com.example.historyofselayar.data.model.Wisata

@Dao
interface WisataDAO {

    @Query("SELECT * FROM WISATA")
    suspend fun getAllWisata() : List<Wisata>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWisata(listWisata: List<Wisata>)

    @Query("SELECT * FROM WISATA WHERE WISATA.nama_wisata LIKE '%' || :nama || '%'")
    suspend fun findWisata(nama : String) : List<Wisata>

}