package com.selayar.history.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DetailWisata(
    val dbID : Int? = -1,
    @PrimaryKey
    val id : Int? = -1,
    val attach: List<Attach>? = arrayListOf(),
    val wisata: Wisata? = null
)