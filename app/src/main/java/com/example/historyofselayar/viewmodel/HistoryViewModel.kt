package com.example.historyofselayar.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.historyofselayar.data.model.DetailWisata
import com.example.historyofselayar.data.model.Wisata
import com.example.historyofselayar.data.repo.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepository: HistoryRepository
) : ViewModel() {

    fun getAllWisata(): MutableState<List<Wisata>> {
        val listWisata = mutableStateOf(listOf<Wisata>())
        viewModelScope.launch {
            val response = historyRepository.getAllWisata()
            response.body()?.let { historyRepository.insertWisata(it) }
            listWisata.value = historyRepository.getLocalWisata()
        }
        return listWisata
    }

    fun getDetailWisata(id : Int) : MutableState<DetailWisata> {
        val detailWisata = mutableStateOf(DetailWisata())
        viewModelScope.launch {
            val response = historyRepository.getDetailWisata(id)
            if (response.isSuccessful && response.body() != null){
                detailWisata.value = response.body()!!
            }
        }
        return detailWisata
    }

    fun searchWisata(value : String): MutableState<List<Wisata>>{
        val searchWisata = mutableStateOf(listOf<Wisata>())
        viewModelScope.launch {
            searchWisata.value = historyRepository.findWisata(value)
        }
        return searchWisata
    }

}