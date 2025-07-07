package com.driverrecords.app.domain

import com.driverrecords.app.data.entities.AppSettings
import com.driverrecords.app.data.entities.DailyRecord

data class CalculationResult(
    val mileagePay: Double,
    val piecePay: Double,
    val overweightPay: Double,
    val totalPay: Double,
    val breakdown: String
)

class PayCalculator {
    
    fun calculateDailyPay(record: DailyRecord, settings: AppSettings): CalculationResult {
        val mileagePay = record.mileage * settings.mileageRate
        val piecePay = (record.dropoffs + record.pickups) * settings.perPieceRate
        val overweightPay = record.overweight * settings.overweightRate
        val totalPay = mileagePay + piecePay + overweightPay
        
        val breakdown = generateBreakdown(record, settings, mileagePay, piecePay, overweightPay, totalPay)
        
        return CalculationResult(
            mileagePay = mileagePay,
            piecePay = piecePay,
            overweightPay = overweightPay,
            totalPay = totalPay,
            breakdown = breakdown
        )
    }
    
    private fun generateBreakdown(
        record: DailyRecord,
        settings: AppSettings,
        mileagePay: Double,
        piecePay: Double,
        overweightPay: Double,
        totalPay: Double
    ): String {
        return """
        === DAILY DRIVING RECORD SUMMARY ===
        Date: ${record.date}
        
        MILEAGE:
          Miles Driven: ${record.mileage}
          Rate: $${String.format("%.2f", settings.mileageRate)} per mile
          Mileage Pay: $${String.format("%.2f", mileagePay)}
        
        DELIVERIES:
          Dropoffs: ${record.dropoffs}
          Pickups: ${record.pickups}
          Total Pieces: ${record.dropoffs + record.pickups}
          Rate: $${String.format("%.2f", settings.perPieceRate)} per piece
          Piece Pay: $${String.format("%.2f", piecePay)}
        
        OVERWEIGHT:
          Overweight Items: ${record.overweight}
          Rate: $${String.format("%.2f", settings.overweightRate)} per item
          Overweight Pay: $${String.format("%.2f", overweightPay)}
        
        TOTAL PAY: $${String.format("%.2f", totalPay)}
        =====================================
        """.trimIndent()
    }
}