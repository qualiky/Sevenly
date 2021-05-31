package com.example.sevenly

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sevenly.databinding.ActivityBmiBinding

class BMIActivity : AppCompatActivity() {

    lateinit var binding: ActivityBmiBinding

    var isMetricSelected = true
    var isImperialSelected = false
    var weight = 0.0
    var height = 0.0
    var bmiData = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarBMI)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbarBMI.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.btnMetric.isSelected = true

        binding.btnMetric.setOnClickListener {
            isMetricSelected = true
            isImperialSelected = false
            binding.textCM.text = "cm"
            binding.textKG.text = "KGs"
        }

        binding.btnImperial.setOnClickListener {
            isMetricSelected = false
            isImperialSelected = true
            binding.textKG.text = "lbs"
            binding.textCM.text = "in"
        }

        binding.btnCalculate.setOnClickListener {
            if(!binding.editTextNumberDecimalHeight.text.isEmpty() || !binding.editTextNumberDecimalWeight.text.isEmpty()) {
                weight = binding.editTextNumberDecimalWeight.text.toString().toDouble()
                height = binding.editTextNumberDecimalHeight.text.toString().toDouble()

                if(isMetricSelected) {
                    bmiData = weight / ((height/100) * (height/100))
                    binding.tvBMI.text = bmiData.toString()

                    setBMIBackground(bmiData)
                } else {
                    var lbs = binding.editTextNumberDecimalWeight.text.toString().toDouble()
                    var ins = binding.editTextNumberDecimalHeight.text.toString().toDouble()

                    weight = lbs * 0.453592
                    height = ins * 0.0254

                    bmiData = weight / (height * height)
                    binding.tvBMI.text = bmiData.toString()

                    setBMIBackground(bmiData)
                }
            }
        }
    }

    private fun setBMIBackground(bmiData: Double) {
        when (bmiData.toInt()) {
            in 1..18 -> binding.tvBMI.setTextColor(Color.parseColor("#05C2EC"))
            in 19..24 -> binding.tvBMI.setTextColor(Color.parseColor("#43DA1D"))
            in 25..29 -> binding.tvBMI.setTextColor(Color.parseColor("#D5D903"))
            in 30..39 -> binding.tvBMI.setTextColor(Color.parseColor("#EABB15"))
            in 40..999 -> binding.tvBMI.setTextColor(Color.parseColor("#EB0404"))
            else -> binding.tvBMI.setTextColor(Color.parseColor("#000000"))

        }
    }
}