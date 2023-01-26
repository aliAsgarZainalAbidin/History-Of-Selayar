package com.selayar.history.data.repo

import com.selayar.history.data.ApiInterface
import com.selayar.history.data.db.WisataDAO
import com.selayar.history.data.model.DetailWisata
import com.selayar.history.data.model.Wisata
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