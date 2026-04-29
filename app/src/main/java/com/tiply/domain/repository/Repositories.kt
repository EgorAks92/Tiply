package com.tiply.domain.repository

import com.tiply.domain.model.*
import kotlinx.coroutines.flow.Flow

interface WaiterRepository {
    fun observeWaiters(): Flow<List<Waiter>>
    suspend fun getWaiter(id: Long): Waiter?
    suspend fun create(firstName: String, lastName: String, pin: String): Long
    suspend fun verifyPin(waiterId: Long, pin: String): Boolean
    suspend fun delete(id: Long)
    suspend fun bindCard(waiterId: Long, panSha256: String): Boolean
    suspend fun getCardBindingHash(waiterId: Long): String?
}
interface TransactionRepository { suspend fun save(tx: Transaction): Long; suspend fun get(id: Long): Transaction?; fun observeByWaiter(waiterId: Long): Flow<List<Transaction>> }
interface SettingsRepository { fun observe(): Flow<AppSettings>; suspend fun updateLanguage(language: AppLanguage); suspend fun updateCurrency(currency: CurrencyCode); suspend fun updateMode(mode: PaymentIntegrationMode); suspend fun updateTerminalId(terminalId: String) }
