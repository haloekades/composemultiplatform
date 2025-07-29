package com.ekades.composemp.quran.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuranSurah(
    val nomor: Int,
    val nama: String,
    val arti: String,
    @SerialName("nama_latin")
    val namaLatin: String,
    @SerialName("jumlah_ayat")
    val jumlahAyat: Int,
    @SerialName("tempat_turun")
    val tempatTurun: String,
    @SerialName("ayat")
    val ayat: List<Ayat>? = null
)