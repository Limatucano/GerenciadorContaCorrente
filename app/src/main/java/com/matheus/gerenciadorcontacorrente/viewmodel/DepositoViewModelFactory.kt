package com.matheus.gerenciadorcontacorrente.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.matheus.gerenciadorcontacorrente.data.repository.ActionsRepository
import java.lang.IllegalArgumentException

class DepositoViewModelFactory(private val repository: ActionsRepository) : ViewModelProvider.Factory{

        override fun <T: ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(DepositoViewModel::class.java)){
                DepositoViewModel(this.repository) as T
            }else{
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
}