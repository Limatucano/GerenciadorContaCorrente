package com.matheus.gerenciadorcontacorrente.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matheus.gerenciadorcontacorrente.data.localstore.Actions
import com.matheus.gerenciadorcontacorrente.data.repository.ActionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExtratoViewModel(private val actionsRepository: ActionsRepository) : ViewModel() {

    val extrato = MutableLiveData<List<Actions>>()

    fun getExtrato(contaCorrente : String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = actionsRepository.getExtrato(contaCorrente)

            extrato.postValue(response)
        }
    }
}