package com.driverrecords.app.data.dao

import androidx.room.*
import com.driverrecords.app.data.entities.DailyRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyRecordDao {
    @Query("SELECT * FROM daily_records ORDER BY date DESC")
    fun getAllRecords(): Flow<List<DailyRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: DailyRecord)

    @Delete
    suspend fun deleteRecord(record: DailyRecord)

    @Query("SELECT * FROM daily_records WHERE id = :id")
    suspend fun getRecordById(id: String): DailyRecord?
}