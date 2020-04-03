package com.example.kotlinmppandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import egger.software.kotlinmpp.add
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calculateButton.setOnClickListener {

            result.text = add(inputA.text.toString(), inputB.text.toString())

        }
    }
}
