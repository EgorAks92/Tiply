package com.tiply.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.tiply.data.local.dao.*
import com.tiply.data.local.entity.*
import com.tiply.domain.model.*
import com.tiply.domain.repository.*
import com.tiply.domain.security.FieldEncryptor
import com.tiply.domain.security.PinHasher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsStore by preferencesDataStore("tiply_settings")

class WaiterRepositoryImpl(private val dao: WaiterDao, private val pinHasher: PinHasher, private val encryptor: FieldEncryptor): WaiterRepository {
 override fun observeWaiters(): Flow<List<Waiter>> = dao.observeAll().map { it.filter { e -> !e.isDeleted }.map { e -> Waiter(e.id,e.firstName,e.lastName,e.cardBindingHashEncrypted!=null,e.cardBindingCreatedAt,e.createdAt,e.updatedAt) } }
 override suspend fun getWaiter(id: Long): Waiter? = dao.getById(id)?.takeIf { !it.isDeleted }?.let { Waiter(it.id,it.firstName,it.lastName,it.cardBindingHashEncrypted!=null,it.cardBindingCreatedAt,it.createdAt,it.updatedAt) }
 override suspend fun create(firstName: String, lastName: String, pin: String): Long { val salt=pinHasher.generateSalt(); val hash=pinHasher.hash(pin,salt); val now=System.currentTimeMillis(); return dao.upsert(WaiterEntity(firstName=firstName,lastName=lastName,cardBindingHashEncrypted=null,cardBindingCreatedAt=null,pinHash=hash,pinSalt=salt,failedPinAttempts=0,pinLockedUntil=null,createdAt=now,updatedAt=now,isDeleted=false)) }
 override suspend fun verifyPin(waiterId: Long, pin: String): Boolean { val w=dao.getById(waiterId)?:return false; val now=System.currentTimeMillis(); if(w.pinLockedUntil?.let{it>now}==true) return false; val ok=pinHasher.verify(pin,w.pinSalt,w.pinHash); if(ok) dao.upsert(w.copy(failedPinAttempts=0,pinLockedUntil=null,updatedAt=now)) else { val attempts=w.failedPinAttempts+1; dao.upsert(w.copy(failedPinAttempts=attempts,pinLockedUntil=if(attempts>=5) now+60_000 else null,updatedAt=now)) }; return ok }
 override suspend fun delete(id: Long) { dao.getById(id)?.let { dao.upsert(it.copy(isDeleted=true,updatedAt=System.currentTimeMillis())) } }
 override suspend fun bindCard(waiterId: Long, panSha256: String): Boolean { val w=dao.getById(waiterId)?:return false; dao.upsert(w.copy(cardBindingHashEncrypted=encryptor.encrypt(panSha256), cardBindingCreatedAt=System.currentTimeMillis(),updatedAt=System.currentTimeMillis())); return true }
 override suspend fun getCardBindingHash(waiterId: Long): String? = dao.getById(waiterId)?.cardBindingHashEncrypted?.let { encryptor.decrypt(it) }
}
class TransactionRepositoryImpl(private val dao: TransactionDao): TransactionRepository {
 override suspend fun save(tx: Transaction): Long = dao.insert(TransactionEntity(tx.id,tx.waiterId,tx.terminalId,tx.externalTransactionId,tx.billAmountMinor,tx.tipAmountMinor,tx.totalAmountMinor,tx.currency.name,tx.status.name,tx.paymentMethod.name,tx.paymentErrorCode,tx.paymentErrorMessage,null,tx.createdAt))
 override suspend fun get(id: Long): Transaction? = dao.getById(id)?.let { Transaction(it.id,it.waiterId,it.terminalId,it.externalTransactionId,it.billAmountMinor,it.tipAmountMinor,it.totalAmountMinor,CurrencyCode.valueOf(it.currency),TransactionStatus.valueOf(it.status),PaymentIntegrationMode.valueOf(it.paymentMethod),it.paymentErrorCode,it.paymentErrorMessage,it.createdAt) }
 override fun observeByWaiter(waiterId: Long): Flow<List<Transaction>> = dao.observeByWaiter(waiterId).map { it.map { e -> Transaction(e.id,e.waiterId,e.terminalId,e.externalTransactionId,e.billAmountMinor,e.tipAmountMinor,e.totalAmountMinor,CurrencyCode.valueOf(e.currency),TransactionStatus.valueOf(e.status),PaymentIntegrationMode.valueOf(e.paymentMethod),e.paymentErrorCode,e.paymentErrorMessage,e.createdAt) } }
}
class SettingsRepositoryImpl(private val context: Context): SettingsRepository {
 private val lang = stringPreferencesKey("lang"); private val cur = stringPreferencesKey("cur"); private val mode = stringPreferencesKey("mode"); private val terminal = stringPreferencesKey("terminal")
 override fun observe(): Flow<AppSettings> = context.settingsStore.data.map { AppSettings(selectedLanguage = AppLanguage.valueOf(it[lang] ?: AppLanguage.RU.name), selectedCurrency = CurrencyCode.valueOf(it[cur] ?: CurrencyCode.RUB.name), selectedPaymentIntegrationMode = PaymentIntegrationMode.valueOf(it[mode] ?: PaymentIntegrationMode.MOCK.name), terminalId = it[terminal] ?: "") }
 override suspend fun updateLanguage(language: AppLanguage) { context.settingsStore.edit { it[lang]=language.name } }
 override suspend fun updateCurrency(currency: CurrencyCode) { context.settingsStore.edit { it[cur]=currency.name } }
 override suspend fun updateMode(modeV: PaymentIntegrationMode) { context.settingsStore.edit { it[mode]=modeV.name } }
 override suspend fun updateTerminalId(terminalId: String) { context.settingsStore.edit { it[terminal]=terminalId } }
}
