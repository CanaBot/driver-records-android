package com.driverrecords.app

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.driverrecords.app.databinding.ActivityMainBinding
import com.driverrecords.app.domain.PayCalculator
import com.driverrecords.app.data.entities.DailyRecord
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val payCalculator = PayCalculator()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        observeViewModel()
    }
    
    private fun setupUI() {
        // Set today's date by default
        binding.editTextDate.setText(dateFormat.format(Date()))
        
        // Calculate button click
        binding.buttonCalculate.setOnClickListener {
            calculateDailyPay()
        }
        
        // Copy button click
        binding.buttonCopy.setOnClickListener {
            copyToClipboard()
        }
        
        // Save Settings button click
        binding.buttonSaveSettings.setOnClickListener {
            saveSettings()
        }
    }
    
    private fun observeViewModel() {
        viewModel.settings.observe(this) { settings ->
            settings?.let {
                binding.editTextMileageRate.setText(it.mileageRate.toString())
                binding.editTextPerPieceRate.setText(it.perPieceRate.toString())
                binding.editTextOverweightRate.setText(it.overweightRate.toString())
            }
        }
    }
    
    private fun calculateDailyPay() {
        try {
            val date = binding.editTextDate.text.toString()
            val mileage = binding.editTextMileage.text.toString().toDoubleOrNull() ?: 0.0
            val dropoffs = binding.editTextDropoffs.text.toString().toIntOrNull() ?: 0
            val pickups = binding.editTextPickups.text.toString().toIntOrNull() ?: 0
            val overweight = binding.editTextOverweight.text.toString().toIntOrNull() ?: 0
            
            val record = DailyRecord(
                id = UUID.randomUUID().toString(),
                date = date,
                mileage = mileage,
                dropoffs = dropoffs,
                pickups = pickups,
                overweight = overweight,
                totalPay = 0.0 // Will be calculated
            )
            
            viewModel.calculateAndSaveRecord(record, payCalculator)
            
        } catch (e: Exception) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun copyToClipboard() {
        val breakdown = binding.textViewResult.text.toString()
        if (breakdown.isNotEmpty()) {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Daily Record", breakdown)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun saveSettings() {
        try {
            val mileageRate = binding.editTextMileageRate.text.toString().toDoubleOrNull() ?: 0.50
            val perPieceRate = binding.editTextPerPieceRate.text.toString().toDoubleOrNull() ?: 1.25
            val overweightRate = binding.editTextOverweightRate.text.toString().toDoubleOrNull() ?: 2.00
            
            viewModel.updateSettings(mileageRate, perPieceRate, overweightRate)
            Toast.makeText(this, "Settings saved!", Toast.LENGTH_SHORT).show()
            
        } catch (e: Exception) {
            Toast.makeText(this, "Please enter valid rates", Toast.LENGTH_SHORT).show()
        }
    }
}