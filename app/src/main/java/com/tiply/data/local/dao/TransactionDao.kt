package com.tiply.data.local.dao

import androidx.room.*
import com.tiply.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao interface TransactionDao {
 @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(entity: TransactionEntity): Long
 @Query("SELECT * FROM transactions WHERE id=:id") suspend fun getById(id: Long): TransactionEntity?
 @Query("SELECT * FROM transactions WHERE waiterId=:waiterId ORDER BY createdAt DESC") fun observeByWaiter(waiterId: Long): Flow<List<TransactionEntity>>
}
