package com.matheus.gerenciadorcontacorrente.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.matheus.gerenciadorcontacorrente.data.repository.UserRepository
import java.lang.IllegalArgumentException

class loginViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory{

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(loginViewModel::class.java)){
            loginViewModel(this.repository) as T
        }else{
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}