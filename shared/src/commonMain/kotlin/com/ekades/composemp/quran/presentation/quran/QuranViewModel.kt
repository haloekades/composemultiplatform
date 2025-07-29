package com.ekades.composemp.quran.presentation.quran

import com.ekades.composemp.quran.data.QuranRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class QuranViewModel(
    private val repository: QuranRepository,
    private val scope: CoroutineScope
) {
    private val _state = MutableStateFlow(QuranState())
    val state: StateFlow<QuranState> = _state.asStateFlow()

    private val _detailPageState = MutableStateFlow(QuranDetailState())
    val detailPageState: StateFlow<QuranDetailState> = _detailPageState.asStateFlow()

    fun dispatch(intent: QuranIntent) {
        when (intent) {
            is QuranIntent.LoadSurahList -> loadSurahs()
        }
    }

    private fun loadSurahs() {
        scope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val data = repository.fetchSurahList()
                _state.value = QuranState(surahList = data)
            } catch (e: Exception) {
                _state.value = QuranState(error = e.message)
            }
        }
    }

    fun loadSurahDetail(number: Int) {
        scope.launch {
            _detailPageState.value = _detailPageState.value.copy(isLoading = true)
            try {
                val data = repository.fetchSurahDetail(number)
                _detailPageState.value = QuranDetailState(surahDetail = data)
            } catch (e: Exception) {
                _detailPageState.value = QuranDetailState(error = e.message)
            }
        }
    }
}