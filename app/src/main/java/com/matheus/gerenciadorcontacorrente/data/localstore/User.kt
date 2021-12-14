package com.matheus.gerenciadorcontacorrente.data.localstore

import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey

@Entity(tableName="User")
data class User (
    @PrimaryKey()
    val contaCorrente : String,
    val nome : String,
    val senha : String,
    val tipoConta : String,
)