package com.tiply.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tiply.data.local.dao.TransactionDao
import com.tiply.data.local.dao.WaiterDao
import com.tiply.data.local.entity.TransactionEntity
import com.tiply.data.local.entity.WaiterEntity

@Database(entities = [WaiterEntity::class, TransactionEntity::class], version = 1)
abstract class TiplyDatabase: RoomDatabase() { abstract fun waiterDao(): WaiterDao; abstract fun transactionDao(): TransactionDao }
