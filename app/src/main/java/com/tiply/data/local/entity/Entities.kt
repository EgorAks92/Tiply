package com.tiply.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "waiters")
data class WaiterEntity(@PrimaryKey(autoGenerate = true) val id: Long = 0, val firstName: String, val lastName: String, val cardBindingHashEncrypted: String?, val cardBindingCreatedAt: Long?, val pinHash: String, val pinSalt: String, val failedPinAttempts: Int, val pinLockedUntil: Long?, val createdAt: Long, val updatedAt: Long, val isDeleted: Boolean)

@Entity(tableName = "transactions")
data class TransactionEntity(@PrimaryKey(autoGenerate = true) val id: Long = 0, val waiterId: Long, val terminalId: String, val externalTransactionId: String?, val billAmountMinor: Long, val tipAmountMinor: Long, val totalAmountMinor: Long, val currency: String, val status: String, val paymentMethod: String, val paymentErrorCode: String?, val paymentErrorMessage: String?, val extraJsonSent: String?, val createdAt: Long)
