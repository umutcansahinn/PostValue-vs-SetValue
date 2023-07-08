package com.umutcansahin.postvaluevssetvalue

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * "value" kullanarak bir mutableLiveData'a değer atadığımızda yaptıgı isi bırakıp bizim verdiğimiz
 * yeni datayı alır.
 *
 * "postValue" kullanarak bir mutableLiveData'a değer atadığımızda bir aciliyeti yoktur ve bu yüzden
 * vereceğimiz data'yı kuyruğa ekler sırası geldiğinde kullanır.
 *
 * value--> sadece Dispatchers.Main'de kullanılır. diger thread'lerde hata fırlattırır.
 * postValue--> herhangi bir thread sınırlaması yoktur tüm thread'lerde kullanılabilir.
 * */

class MainViewModel : ViewModel() {

    private val _state = MutableLiveData<String>()
    val state: LiveData<String> = _state

    init {
        firstUpdateData()
        secondUpdateData()
    }

    private fun firstUpdateData() = viewModelScope.launch(Dispatchers.Main) {
        _state.value = "first value"
    }

    private fun secondUpdateData() = viewModelScope.launch(Dispatchers.IO) {
        _state.postValue("second value")
        //  _state.value = "second value"
    }

}