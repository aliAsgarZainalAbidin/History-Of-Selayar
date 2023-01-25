package com.example.historyofselayar.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.historyofselayar.data.model.Attach
import com.example.historyofselayar.data.model.Wisata

@Entity
data class EntityListWisata(
    @PrimaryKey(autoGenerate = true)
    val dbID : Int,
    val attach: List<Attach>,
    val wisata: Wisata
)
