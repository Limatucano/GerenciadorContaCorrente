package com.matheus.gerenciadorcontacorrente.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.matheus.gerenciadorcontacorrente.data.repository.ActionsRepository
import java.lang.IllegalArgumentException

class MainViewModelFactory(private val repository: ActionsRepository) : ViewModelProvider.Factory{

        override fun <T: ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(MainViewModel::class.java)){
                MainViewModel(this.repository) as T
            }else{
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
}