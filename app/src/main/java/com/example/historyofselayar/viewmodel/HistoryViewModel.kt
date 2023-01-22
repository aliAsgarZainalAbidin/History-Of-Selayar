package com.example.historyofselayar.viewmodel

import androidx.lifecycle.ViewModel
import com.example.historyofselayar.data.repo.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepository: HistoryRepository
) : ViewModel(){

}