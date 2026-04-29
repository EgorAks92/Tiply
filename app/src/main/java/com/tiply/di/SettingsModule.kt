package com.tiply.di

import com.tiply.domain.repository.SettingsRepository
import com.tiply.domain.usecase.settings.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module @InstallIn(SingletonComponent::class)
object SettingsModule {
 @Provides fun observeSettings(repo: SettingsRepository) = ObserveAppSettingsUseCase(repo)
 @Provides fun updateLanguage(repo: SettingsRepository) = UpdateLanguageUseCase(repo)
 @Provides fun updateCurrency(repo: SettingsRepository) = UpdateCurrencyUseCase(repo)
 @Provides fun updateMode(repo: SettingsRepository) = UpdatePaymentIntegrationModeUseCase(repo)
 @Provides fun updateTerminal(repo: SettingsRepository) = UpdateTerminalIdUseCase(repo)
}
