package com.example.wall_e.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _promptValue = MutableStateFlow("")
    val promptValue = _promptValue.asStateFlow()

    private val _promptFieldEnabled = MutableStateFlow(true)
    val promptFieldEnabled = _promptFieldEnabled.asStateFlow()

    private val _executePromptButtonEnabled = MutableStateFlow(false)
    val executePromptButtonEnabled = _executePromptButtonEnabled.asStateFlow()

    private val _recordAudioButtonEnabled = MutableStateFlow(true)
    val recordAudioButtonEnabled = _recordAudioButtonEnabled.asStateFlow()

    fun setPromptValue(value: String) {
        _executePromptButtonEnabled.value = value.isNotEmpty()
        _promptValue.value = value
    }

    fun executePrompt() {
        disableCompleteForm()
        // TODO
    }

    fun recordAudio() {
        disableCompleteForm()
        // TODO
    }

    private fun disableCompleteForm() {
        _promptFieldEnabled.value = false
        _executePromptButtonEnabled.value = false
        _recordAudioButtonEnabled.value = false
    }
}