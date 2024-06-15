/*
Author's name: Hetashvi Ban
StudentID: 200541827
Date: 16th June, 2024
*/

/*
File name: Calculator App - UI design
App Description: The Simple Calculator App is designed to provide users with a straightforward and efficient tool
for performing basic arithmetic operations. Developed using Android Studio and the Kotlin programming language,
this app focuses on creating an intuitive user interface (UI) suitable for both integer and floating-point calculations.
This document outlines the UI design for the app, which is optimized for portrait orientation on Android devices.
*/

//Version Information: https://github.com/Hetashviban/calculator-mobile-app


package com.georgiancollege.calculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.georgiancollege.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
        private lateinit var binding: ActivityMainBinding
        //late init: late initialization

        override fun onCreate(savedInstanceState: Bundle?)
        //fun -- function, function in kotlin starts with fun
        {
            super.onCreate(savedInstanceState)

            //create a reference to the ActivityMainBinding Class object
            binding = ActivityMainBinding.inflate(layoutInflater)

            enableEdgeToEdge()

            setContentView(binding.root)

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
            { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            val calculator = Calculator(binding)
        }
    }