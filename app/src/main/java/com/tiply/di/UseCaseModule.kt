package com.tiply.di

import com.tiply.domain.payment.CardReaderApiRouter
import com.tiply.domain.payment.PaymentApiRouter
import com.tiply.domain.payment.PaymentExtraJsonBuilder
import com.tiply.domain.repository.*
import com.tiply.domain.usecase.payment.*
import com.tiply.domain.usecase.settings.*
import com.tiply.domain.usecase.waiter.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module @InstallIn(SingletonComponent::class)
object UseCaseModule {
 @Provides fun createWaiter(repo: WaiterRepository) = CreateWaiterUseCase(repo)
 @Provides fun observeWaiters(repo: WaiterRepository) = ObserveWaitersUseCase(repo)
 @Provides fun getWaiter(repo: WaiterRepository) = GetWaiterUseCase(repo)
 @Provides fun deleteWaiter(repo: WaiterRepository) = DeleteWaiterUseCase(repo)
 @Provides fun bindCard(repo: WaiterRepository, settings: SettingsRepository, router: CardReaderApiRouter) = BindCardToWaiterUseCase(repo, settings, router)
 @Provides fun verifyPin(repo: WaiterRepository) = VerifyWaiterPinUseCase(repo)
 @Provides fun startPayment(router: PaymentApiRouter, tx: TransactionRepository, builder: PaymentExtraJsonBuilder) = StartPaymentUseCase(router, tx, builder)
 @Provides fun getTx(tx: TransactionRepository) = GetTransactionUseCase(tx)
 @Provides fun observeTx(tx: TransactionRepository) = ObserveWaiterTransactionsUseCase(tx)
 @Provides fun txSummary() = GetWaiterTransactionSummaryUseCase()
 @Provides fun observeSettings(repo: SettingsRepository) = ObserveAppSettingsUseCase(repo)
 @Provides fun updLang(repo: SettingsRepository) = UpdateLanguageUseCase(repo)
 @Provides fun updCur(repo: SettingsRepository) = UpdateCurrencyUseCase(repo)
 @Provides fun updMode(repo: SettingsRepository) = UpdatePaymentIntegrationModeUseCase(repo)
 @Provides fun updTerminal(repo: SettingsRepository) = UpdateTerminalIdUseCase(repo)
}
