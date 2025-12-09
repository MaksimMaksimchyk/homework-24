package com.example.homework_24

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MainActivityViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UIstate>(UIstate.Empty("Тут будет результат"))
    val uiState: StateFlow<UIstate> = _uiState.asStateFlow()

    fun getResult() {
        viewModelScope.launch {
            _uiState.value = UIstate.Loading("Загружаем...")
            try {
                val result = withContext(Dispatchers.IO) {
                    fakeLoad()
                }
                _uiState.value = UIstate.Success(result)
            } catch (e: Exception) {
                _uiState.value = UIstate.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }

    suspend fun fakeLoad(): String {
        delay(1500)
        val random = Random.nextInt()
        if (random % 2 == 0) {
            return "Данные успешно загружены!"
        } else {
            throw Exception("Какая-то ошибка загрузки")
        }
    }

    sealed interface UIstate {
        data class Loading(val message: String) : UIstate
        data class Success(val result: String) : UIstate
        data class Error(val message: String) : UIstate
        data class Empty(val message: String) : UIstate
    }
}