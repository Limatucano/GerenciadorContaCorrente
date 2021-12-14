package com.matheus.gerenciadorcontacorrente.data.localstore

import android.app.Notification
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ActionsDAO {

    @Query("SELECT sum(value) as value FROM Actions WHERE contaCorrente = :contaCorrente AND (actionType = 'saque' OR actionType = 'visita' OR (actionType = 'transferencia' AND actionTo != :contaCorrente))")
    fun getLessValueByUser(contaCorrente : String) : Double

    @Query("SELECT sum(value) as value FROM Actions WHERE contaCorrente = :contaCorrente AND (actionType = 'deposito' OR (actionType = 'transferencia' AND actionTo = :contaCorrente))")
    fun getMoreValueByUser(contaCorrente : String) : Double

    @Insert()
    fun insertNewAction(action : Actions)
}