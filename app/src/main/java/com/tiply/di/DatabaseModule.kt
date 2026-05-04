package com.tiply.di

import android.content.Context
import androidx.room.Room
import com.tiply.data.local.TiplyDatabase
import com.tiply.data.local.dao.TransactionDao
import com.tiply.data.local.dao.WaiterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module @InstallIn(SingletonComponent::class)
object DatabaseModule {
 @Provides @Singleton fun provideDb(@ApplicationContext context: Context): TiplyDatabase = Room.databaseBuilder(context, TiplyDatabase::class.java, "tiply.db").build()
 @Provides fun provideWaiterDao(db: TiplyDatabase): WaiterDao = db.waiterDao()
 @Provides fun provideTransactionDao(db: TiplyDatabase): TransactionDao = db.transactionDao()
}
