package com.tiply.domain.repository

import com.tiply.domain.model.*
import kotlinx.coroutines.flow.Flow

interface WaiterRepository { fun observeWaiters(): Flow<List<Waiter>>; suspend fun getWaiter(id: Long): Waiter?; suspend fun save(waiter: Waiter): Long; suspend fun delete(id: Long); suspend fun bindCard(waiterId: Long, bindingHash: String): Boolean }
interface TransactionRepository { suspend fun save(tx: Transaction): Long; suspend fun get(id: Long): Transaction?; fun observeByWaiter(waiterId: Long): Flow<List<Transaction>> }
interface SettingsRepository { fun observe(): Flow<AppSettings>; suspend fun updateLanguage(language: AppLanguage); suspend fun updateCurrency(currency: CurrencyCode); suspend fun updateMode(mode: PaymentIntegrationMode); suspend fun updateTerminalId(terminalId: String) }
