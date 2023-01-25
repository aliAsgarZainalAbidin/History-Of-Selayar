package com.example.historyofselayar.data.repo

import androidx.room.Database
import com.example.historyofselayar.data.ApiInterface
import com.example.historyofselayar.data.db.EntityListWisata
import com.example.historyofselayar.data.db.WisataDAO
import com.example.historyofselayar.data.model.DetailWisata
import com.example.historyofselayar.data.model.Wisata
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val wisataDAO: WisataDAO
) {

    suspend fun getAllWisata() : Response<List<Wisata>> {
        return apiInterface.getAllWisata()
    }

    suspend fun getDetailWisata(id : Int) : Response<DetailWisata>{
        return apiInterface.getDetailWisata(id)
    }

    suspend fun searchWisata(nama : String): Response<List<Wisata>>{
        return apiInterface.search(nama)
    }

    suspend fun getLocalWisata(): List<Wisata>{
        return wisataDAO.getAllWisata()
    }

    suspend fun insertWisata(listWisata: List<Wisata>){
        wisataDAO.insertWisata(listWisata)
    }

    suspend fun findWisata(nama : String): List<Wisata>{
        return wisataDAO.findWisata(nama)
    }

//    fun getLocalDetailWisata(wisata: Wisata) : EntityListWisata{
//        return wisataDAO.getDetailWisata(wisata)
//    }
}