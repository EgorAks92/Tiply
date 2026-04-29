package com.tiply.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.tiply.data.repository.SettingsRepositoryImpl
import com.tiply.domain.repository.SettingsRepository
import com.tiply.domain.usecase.settings.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module @InstallIn(SingletonComponent::class)
object SettingsModule {
 @Provides @Singleton fun providePrefs(@ApplicationContext context: Context): DataStore<Preferences> = PreferenceDataStoreFactory.create { context.preferencesDataStoreFile("tiply_settings") }
 @Provides @Singleton fun provideSettingsRepo(ds: DataStore<Preferences>): SettingsRepository = SettingsRepositoryImpl(ds)
 @Provides fun observeSettings(repo: SettingsRepository) = ObserveAppSettingsUseCase(repo)
 @Provides fun updateLanguage(repo: SettingsRepository) = UpdateLanguageUseCase(repo)
 @Provides fun updateCurrency(repo: SettingsRepository) = UpdateCurrencyUseCase(repo)
 @Provides fun updateMode(repo: SettingsRepository) = UpdatePaymentIntegrationModeUseCase(repo)
 @Provides fun updateTerminal(repo: SettingsRepository) = UpdateTerminalIdUseCase(repo)
}
