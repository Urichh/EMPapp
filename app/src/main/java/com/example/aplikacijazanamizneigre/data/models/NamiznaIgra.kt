package com.example.aplikacijazanamizneigre.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "namizne_igre")
data class NamiznaIgra(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val igra: String,
    val zanr: String,
    val zahtevnost: String,
    val cena: Double,
    val minIgralcev: Int,
    val maxIgralcev: Int,
    val opis: String,
    val slikaURL: String
)
