package com.ekades.composemp.quran.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Ayat(
    val id: Int,
    val surah: Int,
    val nomor: Int,
    val ar: String,
    val tr: String,
    val idn: String,
)