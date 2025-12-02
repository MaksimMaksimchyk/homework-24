package com.example.homework_24

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MainActivityViewModel : ViewModel() {
    private val _dataFromServer: MutableLiveData<String> = MutableLiveData("Тут будет результат")
    val dataFromServer: LiveData<String> = _dataFromServer

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun getResult() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = withContext(Dispatchers.IO) {
                    fakeLoad()
                }
                _dataFromServer.value = result
                _isLoading.value = false
            } catch (e: Exception) {
                _dataFromServer.value = e.message
                _isLoading.value = false
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
}