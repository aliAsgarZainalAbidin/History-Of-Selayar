package com.selayar.history.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Wisata(
    @PrimaryKey
    val id: Int? =-1,
    val deskripsi: String? = null,
    val foto: String? = null,
    val maps: String? = null,
    val nama_wisata: String? = null,
    val qr_code: String? = null,
)