package com.matheus.gerenciadorcontacorrente.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matheus.gerenciadorcontacorrente.data.localstore.Actions
import com.matheus.gerenciadorcontacorrente.data.repository.ActionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SaqueViewModel (private val actionsRepository: ActionsRepository) : ViewModel() {

    val errorSaque = MutableLiveData<String>()

    fun insertNewAction(action : Actions, tipoConta : String, saldo : Double){
        viewModelScope.launch(Dispatchers.IO) {
            if(tipoConta == "COMUM" && action.value!! > saldo){
                errorSaque.postValue("Usuário com tipo de conta COMUM não pode sacar além do seu saldo")
                return@launch
            }
            actionsRepository.insertNewAction(action)
        }

    }
}