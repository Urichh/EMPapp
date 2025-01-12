package com.example.aplikacijazanamizneigre.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "seznam_zelja")
data class SeznamZelja(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idIgre: Int,
    val dodanDatum: String
)