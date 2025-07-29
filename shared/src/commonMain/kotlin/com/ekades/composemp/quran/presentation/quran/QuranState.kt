package com.ekades.composemp.quran.presentation.quran

import com.ekades.composemp.quran.domain.model.QuranSurah

data class QuranState(
    val isLoading: Boolean = false,
    val surahList: List<QuranSurah> = emptyList(),
    val error: String? = null
)