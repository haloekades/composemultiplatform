package com.ekades.composemp.quran.presentation.quran

import com.ekades.composemp.quran.domain.model.QuranSurah

data class QuranDetailState(
    val isLoading: Boolean = false,
    val surahDetail: QuranSurah? = null,
    val error: String? = null
)