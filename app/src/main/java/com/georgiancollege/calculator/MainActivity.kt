package com.georgiancollege.calculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.georgiancollege.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // Late initialization for the binding object
    private lateinit var binding: ActivityMainBinding
    private var operand: String = "0"
    private var currentNumber: String = ""
    private var currentOperator: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create a reference to the ActivityMainBinding class object
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

    private fun numberButtonClicked(tag: String) {
        when (tag) {
            "." -> if (!operand.contains(".")) operand += "."
            else -> {
                operand = if (operand == "0" || operand == "-0") tag else operand + tag
            }
        }
        updateResultTextView()
    }

    private fun operatorButtonClicked(tag: String) {
        when (tag) {
            "C" -> clearOperand()
            "delete" -> deleteLastDigit()
            "plus_minus" -> togglePlusMinus()
            "plus", "minus", "multiply", "divide", "percent" -> handleOperator(tag)
            "equals" -> performCalculation()
        }
    }

    private fun handleOperator(operator: String) {
        if (currentNumber.isNotEmpty() && currentOperator.isNotEmpty()) {
            performCalculation()
        }
        currentOperator = operator
        currentNumber = operand
        operand = "0"
    }

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


    private fun percentage() {
        operand = (operand.toFloat() / 100).toString()
        if (operand.endsWith(".0")) {
            operand = operand.removeSuffix(".0")
        }
        updateResultTextView()
    }

    private fun clearOperand() {
        operand = "0"
        updateResultTextView()
    }

    private fun deleteLastDigit() {
        if (operand.length == 2 && operand.startsWith("-")) {
            operand = "0"
        } else {
            operand = if (operand.length > 1) operand.dropLast(1) else "0"
        }
        updateResultTextView()
    }

    private fun togglePlusMinus() {
        if (operand != "0") {
            operand = if (operand.startsWith("-")) operand.substring(1) else "-$operand"
        }
        updateResultTextView()
    }

    private fun updateResultTextView() {
        binding.resultTextView.text = operand
    }
}
