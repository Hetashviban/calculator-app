package com.georgiancollege.calculator

import com.georgiancollege.calculator.databinding.ActivityMainBinding

class Calculator (dataBinding: ActivityMainBinding){
    private var binding: ActivityMainBinding = dataBinding
    private var operand: String = "0"

    init {
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
            if (button != null) {
                button.setOnClickListener { numberButtonClicked(button.tag.toString()) }
            }
        }

        // Listener for operator buttons
        operatorButtons.forEach { button ->
            if (button != null) {
                button.setOnClickListener { operatorButtonClicked(button.tag.toString()) }
            }
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
        }
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