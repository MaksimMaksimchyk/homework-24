package com.example.homework_24

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MainActivityViewModel : ViewModel() {
    var result: MutableLiveData<String> = MutableLiveData("Тут будет результат")

    fun getResult() {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    fakeLoad()
                } catch (e: Exception) {
                    result.postValue("Ошибка загрузки")
                }
            }
        }

    }

    suspend fun fakeLoad() {
        delay(1500)
        val random = Random.nextInt()
        if (random % 2 == 0) {
            result.postValue("Данные успешно загружены!")
        } else {
            throw Exception()
        }
    }
}