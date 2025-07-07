package com.driverrecords.app.data.dao

import androidx.room.*
import com.driverrecords.app.data.entities.AppSettings
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    @Query("SELECT * FROM app_settings WHERE id = 1")
    fun getSettings(): Flow<AppSettings?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSettings(settings: AppSettings)
}
