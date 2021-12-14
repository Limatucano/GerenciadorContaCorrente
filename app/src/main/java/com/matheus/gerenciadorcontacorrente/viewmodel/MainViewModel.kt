package com.matheus.gerenciadorcontacorrente.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matheus.gerenciadorcontacorrente.data.repository.ActionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val actionsRepository: ActionsRepository) : ViewModel(){
    val saldo = MutableLiveData<String>()

    fun getValueByuser(contaCorrente : String) {
        viewModelScope.launch(Dispatchers.IO) {
            val valorRetirado = actionsRepository.getLessValueByUser(contaCorrente)
            val valorAdd = actionsRepository.getMoreValueByUser(contaCorrente)

            val result = valorAdd - valorRetirado
            Log.d("TESTE - add", valorAdd.toString())
            Log.d("TESTE - retirado", valorRetirado.toString())

            saldo.postValue(result.toString())
        }
    }
}