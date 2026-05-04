package com.tiply.di

import com.tiply.data.local.dao.TransactionDao
import com.tiply.data.local.dao.WaiterDao
import com.tiply.data.repository.TransactionRepositoryImpl
import com.tiply.data.repository.WaiterRepositoryImpl
import com.tiply.domain.repository.TransactionRepository
import com.tiply.domain.repository.WaiterRepository
import com.tiply.domain.security.FieldEncryptor
import com.tiply.domain.security.PinHasher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module @InstallIn(SingletonComponent::class)
object RepositoryModule {
 @Provides @Singleton fun provideWaiterRepo(dao: WaiterDao, pinHasher: PinHasher, encryptor: FieldEncryptor): WaiterRepository = WaiterRepositoryImpl(dao,pinHasher,encryptor)
 @Provides @Singleton fun provideTxRepo(dao: TransactionDao): TransactionRepository = TransactionRepositoryImpl(dao)
}