package com.matheus.gerenciadorcontacorrente.data.localstore

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.time.OffsetDateTime

@Entity(
    tableName="Actions",
    foreignKeys = [
        ForeignKey(
            entity = User::class,parentColumns = ["contaCorrente"], childColumns = ["contaCorrente"], onDelete = ForeignKey.CASCADE)
    ])
data class Actions (
    @PrimaryKey(autoGenerate = true)
    val actionId: Int? = null,
    val actionType : String?,
    val value : Double? = null,
    val date : OffsetDateTime? = null,
    val description : String?,
    val actionTo : String?,
    val contaCorrente : String?,
)