package com.tiply.data.local.dao

import androidx.room.*
import com.tiply.data.local.entity.WaiterEntity
import kotlinx.coroutines.flow.Flow

@Dao interface WaiterDao {
 @Query("SELECT * FROM waiters ORDER BY createdAt DESC") fun observeAll(): Flow<List<WaiterEntity>>
 @Query("SELECT * FROM waiters WHERE id=:id") suspend fun getById(id: Long): WaiterEntity?
 @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun upsert(entity: WaiterEntity): Long
 @Query("DELETE FROM waiters WHERE id=:id") suspend fun deleteById(id: Long)
}
