/*
Author's name: Hetashvi Ban
StudentID: 200541827
Date: 16th June, 2024
*/

/*
File name: Calculator App - functionality
App Description: The Simple Calculator App is designed to provide users with a straightforward and efficient tool
for performing basic arithmetic operations. Developed using Android Studio and the Kotlin programming language,
this app focuses on creating an intuitive user interface (UI) suitable for both integer and floating-point calculations.
This document outlines the UI design and functionality for the app, which is optimized for portrait orientation on Android devices.
*/

//Version Information: https://github.com/Hetashviban/calculator-app

package com.georgiancollege.calculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.georgiancollege.calculator.databinding.ActivityMainBinding

//MainActivity class for the Calculator app.
class MainActivity : AppCompatActivity() {
    // Late initialization for the binding object
    private lateinit var binding: ActivityMainBinding
    private var operand: String = "0"
    private var currentNumber: String = ""
    private var currentOperator: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setListeners()
    }

    //Function to set listeners for each button
    private fun setListeners() {
        // Number buttons
        val numberButtons = listOf(
            binding.zeroButton, binding.oneButton, binding.twoButton,
            binding.threeButton, binding.fourButton, binding.fiveButton,
            binding.sixButton, binding.sevenButton, binding.eightButton,
            binding.nineButton, binding.decimalButton
        )

        // Operator buttons
        val operatorButtons = listOf(
            binding.clearButton, binding.percentButton, binding.deleteButton,
            binding.divideButton, binding.multiplyButton, binding.minusButton,
            binding.plusButton, binding.equalsButton, binding.plusMinusButton
        )

        // Listener for number buttons
        numberButtons.forEach { button ->
            button?.setOnClickListener { numberButtonClicked(button.tag.toString()) }
        }

        // Listener for operator buttons
        operatorButtons.forEach { button ->
            button?.setOnClickListener { operatorButtonClicked(button.tag.toString()) }
        }
    }

    //Handles clicks on number buttons.
    private fun numberButtonClicked(tag: String) {
        when (tag) {
            "." -> if (!operand.contains(".")) operand += "."
            else -> {
                operand = if (operand == "0" || operand == "-0") tag else operand + tag
            }
        }
        updateResultTextView()
    }

    //Handles clicks on operator buttons.
    private fun operatorButtonClicked(tag: String) {
        when (tag) {
            "C" -> clearOperand()
            "delete" -> deleteLastDigit()
            "plus_minus" -> togglePlusMinus()
            "plus", "minus", "multiply", "divide", "percent" -> handleOperator(tag)
            "equals" -> performCalculation()
        }
    }

    //Handles the selected operator.
    private fun handleOperator(operator: String) {
        if (currentNumber.isNotEmpty() && currentOperator.isNotEmpty()) {
            performCalculation()
        }
        currentOperator = operator
        currentNumber = operand
        operand = "0"
    }

    //Performs the calculation based on the current operator.
    private fun performCalculation() {
        if (currentNumber.isNotEmpty() && currentOperator.isNotEmpty()) {
            when (currentOperator) {
                "plus" -> add()
                "minus" -> subtract()
                "multiply" -> multiply()
                "divide" -> divide()
                "percent" -> percentage()
            }
            currentOperator = ""
            currentNumber = operand
        }
    }

    //Adds the current number and the operand.
    private fun add() {
        operand = if (currentNumber.contains(".") || operand.contains(".")) {
            (currentNumber.toFloat() + operand.toFloat()).toString()
        } else {
            (currentNumber.toInt() + operand.toInt()).toString()
        }
        if (operand.endsWith(".0")) {
            operand = operand.removeSuffix(".0")
        }
        updateResultTextView()
    }

    //Subtracts the operand from the current number.
    private fun subtract() {
        operand = if (currentNumber.contains(".") || operand.contains(".")) {
            (currentNumber.toFloat() - operand.toFloat()).toString()
        } else {
            (currentNumber.toInt() - operand.toInt()).toString()
        }
        if (operand.endsWith(".0")) {
            operand = operand.removeSuffix(".0")
        }
        updateResultTextView()
    }

    //Multiplies the current number and the operand.
    private fun multiply() {
        operand = if (currentNumber.contains(".") || operand.contains(".")) {
            (currentNumber.toFloat() * operand.toFloat()).toString()
        } else {
            (currentNumber.toInt() * operand.toInt()).toString()
        }
        if (operand.endsWith(".0")) {
            operand = operand.removeSuffix(".0")
        }
        updateResultTextView()
    }

    // Divides the current number by the operand.
    private fun divide() {
        if (operand == "0") {
            operand = "Error" // Handling division by zero
        } else {
            operand = (currentNumber.toFloat() / operand.toFloat()).toString()
            if (operand.endsWith(".0")) {
                operand = operand.removeSuffix(".0")
            }
        }
        updateResultTextView()
    }


    //percent operator
    private fun percentage() {
        operand = (operand.toFloat() / 100).toString()
        if (operand.endsWith(".0")) {
            operand = operand.removeSuffix(".0")
        }
        updateResultTextView()
    }

    //Clears the operand.
    private fun clearOperand() {
        operand = "0"
        updateResultTextView()
    }

    //Deletes the last digit of the operand.
    private fun deleteLastDigit() {
        if (operand.length == 2 && operand.startsWith("-")) {
            operand = "0"
        } else {
            operand = if (operand.length > 1) operand.dropLast(1) else "0"
        }
        updateResultTextView()
    }

    //Toggles the operand between positive and negative.
    private fun togglePlusMinus() {
        if (operand != "0") {
            operand = if (operand.startsWith("-")) operand.substring(1) else "-$operand"
        }
        updateResultTextView()
    }

    //Updates the result TextView with the current operand.
    private fun updateResultTextView() {
        binding.resultTextView.text = operand
    }
}
