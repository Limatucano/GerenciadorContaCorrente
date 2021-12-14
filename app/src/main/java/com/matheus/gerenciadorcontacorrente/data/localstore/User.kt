package com.matheus.gerenciadorcontacorrente.data.localstore

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName="User")
data class User (
    @PrimaryKey()
    val contaCorrente : String,
    val nome : String,
    val senha : String,
    val tipoConta : String,
)
