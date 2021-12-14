package com.matheus.gerenciadorcontacorrente.data.repository

import com.matheus.gerenciadorcontacorrente.data.localstore.Actions
import com.matheus.gerenciadorcontacorrente.data.localstore.ActionsDAO

class ActionsRepository(private val actionsDAO: ActionsDAO) {

    fun getLessValueByUser(contaCorrente : String) : Double = actionsDAO.getLessValueByUser(contaCorrente)

    fun getMoreValueByUser(contaCorrente : String) : Double = actionsDAO.getMoreValueByUser(contaCorrente)

    fun insertNewAction(action : Actions) = actionsDAO.insertNewAction(action)
}