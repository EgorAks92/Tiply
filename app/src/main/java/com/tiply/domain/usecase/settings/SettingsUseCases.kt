package com.tiply.domain.usecase.settings

import com.tiply.domain.model.*
import com.tiply.domain.repository.SettingsRepository

class ObserveAppSettingsUseCase(private val repo: SettingsRepository){ operator fun invoke()=repo.observe() }
class UpdateLanguageUseCase(private val repo: SettingsRepository){ suspend operator fun invoke(v: AppLanguage)=repo.updateLanguage(v) }
class UpdateCurrencyUseCase(private val repo: SettingsRepository){ suspend operator fun invoke(v: CurrencyCode)=repo.updateCurrency(v) }
class UpdatePaymentIntegrationModeUseCase(private val repo: SettingsRepository){ suspend operator fun invoke(v: PaymentIntegrationMode)=repo.updateMode(v) }
class UpdateTerminalIdUseCase(private val repo: SettingsRepository){ suspend operator fun invoke(v: String)=repo.updateTerminalId(v) }
