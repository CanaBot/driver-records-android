package com.driverrecords.app.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_records")
data class DailyRecord(
    @PrimaryKey val id: String,
    val date: String,
    val mileage: Double,
    val dropoffs: Int,
    val pickups: Int,
    val overweight: Int,
    val totalPay: Double
)