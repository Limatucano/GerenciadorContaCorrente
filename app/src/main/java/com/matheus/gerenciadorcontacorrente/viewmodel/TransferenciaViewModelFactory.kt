package com.matheus.gerenciadorcontacorrente.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.matheus.gerenciadorcontacorrente.data.repository.ActionsRepository
import com.matheus.gerenciadorcontacorrente.data.repository.UserRepository
import java.lang.IllegalArgumentException

class TransferenciaViewModelFactory(private val repository: ActionsRepository, private val userRepository: UserRepository) : ViewModelProvider.Factory{

        override fun <T: ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(TransferenciaViewModel::class.java)){
                TransferenciaViewModel(this.repository, this.userRepository) as T
            }else{
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
}