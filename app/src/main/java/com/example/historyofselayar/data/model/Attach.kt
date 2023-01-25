package com.example.historyofselayar.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Attach(
    @PrimaryKey(autoGenerate = true)
    val dbID : Int? = -1,
    val attach_name: String,
    val id: Int,
    val wisata_id: Int
)