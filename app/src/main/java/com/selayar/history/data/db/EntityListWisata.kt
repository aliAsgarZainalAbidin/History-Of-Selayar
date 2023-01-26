package com.selayar.history.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.selayar.history.data.model.Attach
import com.selayar.history.data.model.Wisata

@Entity
data class EntityListWisata(
    @PrimaryKey(autoGenerate = true)
    val dbID : Int,
    val attach: List<Attach>,
    val wisata: Wisata
)
