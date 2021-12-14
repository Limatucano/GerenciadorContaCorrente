package com.matheus.gerenciadorcontacorrente.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matheus.gerenciadorcontacorrente.data.localstore.User
import com.matheus.gerenciadorcontacorrente.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection

class loginViewModel(private val userRepository: UserRepository) : ViewModel(){

    val loginStatusCode = MutableLiveData<Int>()
    val userLogado = MutableLiveData<User>()
    fun getUserByContaCorrente(contaCorrente : String, senha : String) {
        viewModelScope.launch(Dispatchers.IO) {
            val userResponse = userRepository.getUserByContaCorrente(contaCorrente)
            userResponse.let { user ->
                if(user?.senha == senha) {
                    userLogado.postValue(user)
                    loginStatusCode.postValue(HttpURLConnection.HTTP_OK)
                    return@launch
                }
                loginStatusCode.postValue(HttpURLConnection.HTTP_UNAUTHORIZED)
            }
        }
    }

    fun insertNewUser(user : User){
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.insertUser(user)
        }
    }

}