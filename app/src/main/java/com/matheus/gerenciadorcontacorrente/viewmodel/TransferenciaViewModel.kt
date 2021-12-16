package com.matheus.gerenciadorcontacorrente.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matheus.gerenciadorcontacorrente.data.localstore.Actions
import com.matheus.gerenciadorcontacorrente.data.localstore.User
import com.matheus.gerenciadorcontacorrente.data.repository.ActionsRepository
import com.matheus.gerenciadorcontacorrente.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

class TransferenciaViewModel(private val actionsRepository: ActionsRepository, private val userRepository: UserRepository) : ViewModel() {

    val errorTransferencia = MutableLiveData<String>()
    @RequiresApi(Build.VERSION_CODES.O)
    fun insertNewAction(action : Actions, minhaTipoConta : String) {
        viewModelScope.launch(Dispatchers.IO) {

            val user : User?= action.actionTo?.let { userRepository.getUserByContaCorrente(it) }
            if(user == null){
                errorTransferencia.postValue("Usuário não encontrado")
                return@launch
            }
            if(action.actionTo == action.contaCorrente){
                errorTransferencia.postValue("Não é possível transferir para si mesmo")
                return@launch
            }
            if(minhaTipoConta == "COMUM" && action.value!! > 1000.0){
                errorTransferencia.postValue("Usuário com tipo de conta COMUM pode transferir até R$ 1000,00")
                return@launch
            }
            when(minhaTipoConta){
                "VIP" -> {
                    val valueTarifaVIP = (0.8/100) * action.value!!
                    val tarifaVIP = Actions(
                        actionType = "tarifa",
                        value = valueTarifaVIP,
                        date = OffsetDateTime.now(),
                        description = "Tarifa de transferência",
                        actionTo = "",
                        contaCorrente = action.contaCorrente
                    )
                    actionsRepository.insertNewAction(tarifaVIP)
                }
                "COMUM" -> {
                    val tarifaComum = Actions(
                        actionType = "tarifa",
                        value = 8.0,
                        date = OffsetDateTime.now(),
                        description = "Tarifa de transferência",
                        actionTo = "",
                        contaCorrente = action.contaCorrente
                    )
                    actionsRepository.insertNewAction(tarifaComum)
                }
            }

            actionsRepository.insertNewAction(action)
        }
    }
}