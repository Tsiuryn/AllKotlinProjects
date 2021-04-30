package com.example.workwithedittext.number

import android.annotation.SuppressLint
import android.content.Context
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged

@SuppressLint("SetTextI18n")
class NumberControl(
    private val editText: EditText,
    private val defaultValue: Double? = null,
    private val minValue: Int = -1,
    private val maxValue: Int = -1,
    private val minLabel: String = "Below minimum value - $minValue",
    private val maxLabel: String = "Greater than the maximum value $maxValue",
    private val fraction: Boolean = false,
    private val postfix: String = "",
    private val separator: Separator = Separator.DOT,
    private val digitCapacity: Int = -1,
    private val supplDigitCapacity: Boolean = false,
    private val listener: (ErrorMessage) -> Unit
) {

    private val TAG = "TAG11"
    private var isAddedComma = false

    private var cursorPosition: Int = 0
    private var listNumber = ArrayList<String>()
    private var addedSeparator = ""

    init {
        var ignore = false
        defaultValue?.toString()?.toCharArray()?.forEach { listNumber.add(it.toString()) }
        editText.setText(getTextFromList(true))
        editText.setSelection(0)

        editText.doOnTextChanged { text, start, before, count ->
            val isBackSpace = before > count
            if (ignore) return@doOnTextChanged
            ignore = true

            cursorPosition = editText.selectionStart
            if (isBackSpace) {
                deleteSymbol()
            } else {
                addSymbol(text!!.substring(cursorPosition - 1, cursorPosition))
            }
            val endText = getTextFromList(true)
            editText.setText(endText)
            setSelectionCursor(endText)
            ignore = false
        }

        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                listener(ErrorMessage.HAS_FOCUS)
            }else{
                checkMaxMin(getTextFromList(false).toDoubleOrNull())
                supplyDigitCapacity()
            }
        }
    }

    private fun supplyDigitCapacity() {
        if(supplDigitCapacity && digitCapacity > -1){
            if(isAddedComma){

            }else{
                for (i in 0 until digitCapacity + 1){
                    listNumber.add(if(i == 0) separator.char else "0")
                }

                    editText.setText(getTextFromList(true))
                cursorPosition = listNumber.size
            }
//
        }
    }

    private fun checkMaxMin (value: Double?){
        if(value != null){
            if(value > maxValue && maxValue != -1 ) listener(ErrorMessage.MAXIMUM)
            if(value < minValue && minValue != -1 ) listener(ErrorMessage.MINIMUM)
        }
    }

    private fun deleteSymbol() {
        cursorPosition = editText.selectionStart
        if (listNumber.size > 0) {
            if (cursorPosition < listNumber.size) {

                checkDeletedSymbol(deletedSymbol = listNumber[cursorPosition])
                listNumber.removeAt(cursorPosition)
            } else {
//                checkDeletedSymbol(deletedSymbol = listNumber[listNumber.size - 1])
//                listNumber.removeAt(listNumber.size - 1) //todo is need delete symbol?
                cursorPosition = listNumber.size
            }
        } else {
            cursorPosition = 0
        }
    }

    private fun checkDeletedSymbol(deletedSymbol: String) {
        if (deletedSymbol == "," || deletedSymbol == ".") {
            isAddedComma = false
        }
    }

    private fun setSelectionCursor(endText: String) {
        if (cursorPosition <= endText.length) {
            editText.setSelection(cursorPosition)
        } else editText.setSelection(listNumber.size)
    }

    private fun addSymbol(addedSymbol: String) {
        checkCursorPosition()
        val index = cursorPosition - 1

        when {
            addedSymbol.matches(Regex("[0-9]")) -> {
                checkIndexAndAdd(index, addedSymbol, false)
            }
            addedSymbol == "." && separator == Separator.DOT -> {
                if (!isAddedComma && fraction) {
                    checkIndexAndAdd(index, addedSymbol, true)
                    isAddedComma = true
                } else cursorPosition--

            }
            addedSymbol == "," && separator == Separator.COMMA -> {
                if (!isAddedComma && fraction) {
                    checkIndexAndAdd(index, addedSymbol, true)
                    isAddedComma = true
                } else cursorPosition--
            }
            else -> cursorPosition--
        }
    }

    private fun checkIndexAndAdd(index: Int, addedSymbol: String, isComma: Boolean) {
        if (index < listNumber.size && index >= 0) {

            if (isNotMoreThanDigitCapacity()) {
                listNumber.add(index, addedSymbol)
                cursorPosition = index + 1
            } else cursorPosition--
            if (isComma) {
                addedSeparator = addedSymbol
            }

        } else {

            if (isNotMoreThanDigitCapacity()) {
                listNumber.add(addedSymbol)
            } else cursorPosition--
            if (isComma) {
                addedSeparator = addedSymbol
            }

        }
    }

    private fun isNotMoreThanDigitCapacity(): Boolean {
        if (isAddedComma && digitCapacity > -1 ) {
            val indexSeparator = getIndexSeparator()
            if(cursorPosition > indexSeparator + 1){
                val isMore = digitCapacity > listNumber.size - indexSeparator
                if(!isMore) listener(ErrorMessage.DIGIT_CAPACITY)
                return isMore
            }

        }
        return true
    }

    private fun getIndexSeparator(): Int {
        return listNumber.indexOf(addedSeparator)
    }

    private fun checkCursorPosition() {
        val maxCursorPosition = listNumber.size + 1
        if (cursorPosition > maxCursorPosition) {
            cursorPosition = maxCursorPosition
        }
    }

    private fun getTextFromList(withPostfix: Boolean): String {
        var text = ""
        listNumber.forEach {
            text += it
        }
        return if(withPostfix)"$text $postfix" else text
    }

    fun check(context: Context) {

        Toast.makeText(
            context,
            "Cursor - $cursorPosition, listSize - ${listNumber.size}",
            Toast.LENGTH_SHORT
        ).show()
    }

}

enum class Separator (val char: String) {
    DOT("."), COMMA (",")
}

enum class ErrorMessage {
    HAS_FOCUS, MINIMUM, MAXIMUM, DIGIT_CAPACITY
}