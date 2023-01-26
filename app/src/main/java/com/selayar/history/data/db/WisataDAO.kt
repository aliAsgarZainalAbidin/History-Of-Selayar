package com.selayar.history.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.selayar.history.data.model.Wisata

@Dao
interface WisataDAO {

    @Query("SELECT * FROM WISATA")
    suspend fun getAllWisata() : List<Wisata>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWisata(listWisata: List<Wisata>)

    @Query("SELECT * FROM WISATA WHERE WISATA.nama_wisata LIKE '%' || :nama || '%'")
    suspend fun findWisata(nama : String) : List<Wisata>

}