package com.tiply.data.repository

import com.tiply.data.local.dao.*
import com.tiply.data.local.entity.*
import com.tiply.domain.model.*
import com.tiply.domain.repository.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class WaiterRepositoryImpl(private val dao: WaiterDao): WaiterRepository {
 override fun observeWaiters(): Flow<List<Waiter>> = dao.observeAll().map { it.map { e -> Waiter(e.id,e.firstName,e.lastName,e.cardBindingHashEncrypted!=null,e.cardBindingCreatedAt,e.createdAt,e.updatedAt) } }
 override suspend fun getWaiter(id: Long): Waiter? = dao.getById(id)?.let { Waiter(it.id,it.firstName,it.lastName,it.cardBindingHashEncrypted!=null,it.cardBindingCreatedAt,it.createdAt,it.updatedAt) }
 override suspend fun save(waiter: Waiter): Long = dao.upsert(WaiterEntity(waiter.id,waiter.firstName,waiter.lastName,null,waiter.cardBindingCreatedAt,"","",0,null,waiter.createdAt,waiter.updatedAt,false))
 override suspend fun delete(id: Long) = dao.deleteById(id)
 override suspend fun bindCard(waiterId: Long, bindingHash: String): Boolean { val w=dao.getById(waiterId)?:return false; dao.upsert(w.copy(cardBindingHashEncrypted=bindingHash, cardBindingCreatedAt=System.currentTimeMillis())); return true }
}
class TransactionRepositoryImpl(private val dao: TransactionDao): TransactionRepository {
 override suspend fun save(tx: Transaction): Long = dao.insert(TransactionEntity(tx.id,tx.waiterId,tx.terminalId,tx.externalTransactionId,tx.billAmountMinor,tx.tipAmountMinor,tx.totalAmountMinor,tx.currency.name,tx.status.name,tx.paymentMethod.name,tx.paymentErrorCode,tx.paymentErrorMessage,null,tx.createdAt))
 override suspend fun get(id: Long): Transaction? = dao.getById(id)?.let { Transaction(it.id,it.waiterId,it.terminalId,it.externalTransactionId,it.billAmountMinor,it.tipAmountMinor,it.totalAmountMinor,CurrencyCode.valueOf(it.currency),TransactionStatus.valueOf(it.status),PaymentIntegrationMode.valueOf(it.paymentMethod),it.paymentErrorCode,it.paymentErrorMessage,it.createdAt) }
 override fun observeByWaiter(waiterId: Long): Flow<List<Transaction>> = dao.observeByWaiter(waiterId).map { it.map { e -> Transaction(e.id,e.waiterId,e.terminalId,e.externalTransactionId,e.billAmountMinor,e.tipAmountMinor,e.totalAmountMinor,CurrencyCode.valueOf(e.currency),TransactionStatus.valueOf(e.status),PaymentIntegrationMode.valueOf(e.paymentMethod),e.paymentErrorCode,e.paymentErrorMessage,e.createdAt) } }
}
class SettingsRepositoryImpl: SettingsRepository {
 private val state= MutableStateFlow(AppSettings())
 override fun observe(): Flow<AppSettings> = state
 override suspend fun updateLanguage(language: AppLanguage) { state.value=state.value.copy(selectedLanguage = language) }
 override suspend fun updateCurrency(currency: CurrencyCode) { state.value=state.value.copy(selectedCurrency = currency) }
 override suspend fun updateMode(mode: PaymentIntegrationMode) { state.value=state.value.copy(selectedPaymentIntegrationMode = mode) }
 override suspend fun updateTerminalId(terminalId: String) { state.value=state.value.copy(terminalId = terminalId) }
}
