package com.driverrecords.app.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_settings")
data class AppSettings(
    @PrimaryKey val id: Int = 1,
    val mileageRate: Double = 0.50,
    val perPieceRate: Double = 1.25,
    val overweightRate: Double = 2.00
)