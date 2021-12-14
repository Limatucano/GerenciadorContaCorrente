package com.matheus.gerenciadorcontacorrente.data.localstore

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User)

    @Query("SELECT * FROM User WHERE contaCorrente = :contaCorrente")
    fun getUserByContaCorrente(contaCorrente : String) : User


}