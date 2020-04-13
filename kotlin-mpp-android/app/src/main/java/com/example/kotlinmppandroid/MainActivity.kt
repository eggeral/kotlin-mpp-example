package com.example.kotlinmppandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calculateButton.setOnClickListener {

            val numberA = inputA.text.toString().toDoubleOrNull() ?: 0.0
            val numberB = inputB.text.toString().toDoubleOrNull() ?: 0.0
            val resultValue = numberA + numberB
            result.text = "Result: $resultValue"

        }
    }
}
