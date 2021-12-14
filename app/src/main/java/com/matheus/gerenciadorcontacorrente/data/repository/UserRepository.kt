package com.matheus.gerenciadorcontacorrente.data.repository

import com.matheus.gerenciadorcontacorrente.data.localstore.Actions
import com.matheus.gerenciadorcontacorrente.data.localstore.User
import com.matheus.gerenciadorcontacorrente.data.localstore.UserDAO

class UserRepository(private val userDAO: UserDAO) {

    fun insertUser(user: User) = userDAO.insertUser(user)

    fun getUserByContaCorrente(contaCorrente : String) : User = userDAO.getUserByContaCorrente(contaCorrente)
}