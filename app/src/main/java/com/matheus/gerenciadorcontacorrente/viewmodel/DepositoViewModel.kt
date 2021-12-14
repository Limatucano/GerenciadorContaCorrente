package com.matheus.gerenciadorcontacorrente.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matheus.gerenciadorcontacorrente.data.localstore.Actions
import com.matheus.gerenciadorcontacorrente.data.repository.ActionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DepositoViewModel(private val actionsRepository: ActionsRepository) : ViewModel() {

    fun insertNewAction(action : Actions) {
        viewModelScope.launch(Dispatchers.IO) {
            actionsRepository.insertNewAction(action)
        }
    }
}