package com.example.workwithedittext.number

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged

@SuppressLint("SetTextI18n")
class NumberControl(
    private val editText: EditText,
    private val defaultValue: String? = null,
    private val minValue: Int = -1,
    private val maxValue: Int = -1,
    private val fraction: Boolean = false,
    private val postfix: String = "",
    private val separator: Separator = Separator.DOT,
    private val digitCapacity: Int = -1,
    private val supplDigitCapacity: Boolean = false,
    private val listener: (value: String, ErrorMessage) -> Unit
) {

    private val TAG = "TAG11"
    private var isAddedComma = false

    private var cursorPosition: Int = 0
    private var listNumber = ArrayList<String>()
    private var ignore = false

    init {
        setUpListByDefaultValue(defaultValue)
        editText.setText(getTextFromList(true))
        editText.setSelection(0)

        editText.doOnTextChanged { text, start, before, count ->
            Log.d(TAG, "$before, $count: ")
            val isBackSpace = before > count
            if (ignore) return@doOnTextChanged
            ignore = true
            if (before - count > 2 || count - before > 2) {
                listNumber.clear()
                isAddedComma = false
                val endText = getTextFromList(true)
                editText.setText(endText)
                setSelectionCursor(endText)
                ignore = false
                return@doOnTextChanged
            }

            cursorPosition = editText.selectionStart
            if (isBackSpace) {
                deleteSymbol()
            } else {
                addSymbol(text!!.substring(cursorPosition - 1, cursorPosition))
            }
            val endText = getTextFromList(true)
            editText.setText(endText)
            setSelectionCursor(endText)
            listener(endText, ErrorMessage.CHANGED)
            ignore = false
        }

        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                listener(editText.text.toString(), ErrorMessage.HAS_FOCUS)
            } else {
                checkMaxMin(getTextFromList(false).toDoubleOrNull())
                supplyDigitCapacity()
            }
        }
    }

    private fun setUpListByDefaultValue(defaultValue: String?) {
        defaultValue?.toCharArray()?.forEach {
            if (it.toString().matches(Regex("[0-9]"))) {
                listNumber.add(it.toString())
            } else {
                listNumber.add(separator.char)
                isAddedComma = true
            }

        }
    }

    private fun supplyDigitCapacity() {
        if (supplDigitCapacity && listNumber.size > 0) {
            val numb = getTextFromList(false).replace(",", ".").toDouble()
            if (digitCapacity > -1) {
                val text = String.format("%.${digitCapacity}f", numb)
                listNumber.clear()
                setUpListByDefaultValue(text)
                ignore = true
                editText.setText(getTextFromList(true))
                ignore = false
            }
        }
    }

    private fun checkMaxMin(value: Double?) {
        if (value != null) {
            if (value > maxValue && maxValue != -1) listener(
                editText.text.toString(),
                ErrorMessage.MAXIMUM
            )
            if (value < minValue && minValue != -1) listener(
                editText.text.toString(),
                ErrorMessage.MINIMUM
            )
        }
    }

    private fun deleteSymbol() {
        cursorPosition = editText.selectionStart
        if (listNumber.size > 0) {
            if (cursorPosition < listNumber.size) {

                checkDeletedSymbol(deletedSymbol = listNumber[cursorPosition])
                listNumber.removeAt(cursorPosition)
            } else {
                cursorPosition = listNumber.size
            }
        } else {
            cursorPosition = 0
        }
    }

    private fun checkDeletedSymbol(deletedSymbol: String) {
        if (deletedSymbol == separator.char) {
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
                checkIndexAndAdd(index, addedSymbol)
            }
            addedSymbol == separator.char && fraction-> {
                if (!isAddedComma ) {
                    isAddedComma = true
                    checkIndexAndAdd(index, addedSymbol)
                } else cursorPosition--

            }

            else -> cursorPosition--
        }
    }

    private fun checkIndexAndAdd(index: Int, addedSymbol: String) {
        if (addedSymbol == separator.char) {
            if (index < 1 || index > listNumber.size) {
                listNumber.add("0")
                listNumber.add(separator.char)
                cursorPosition += 1
                return
            }
        }
        if (addedSymbol == "0" && index == 0) {
            if(fraction && !isAddedComma){
                listNumber.add(index, "0")
                listNumber.add(index + 1, separator.char)
                cursorPosition += 1
                isAddedComma = true
                return
            } else{
                cursorPosition --
                return
            }
        }

        if (index < listNumber.size && index >= 0) {


            if (isNotMoreThanDigitCapacity()) {
                listNumber.add(index, addedSymbol)
                cursorPosition = index + 1

            } else cursorPosition--


        } else {

            if (isNotMoreThanDigitCapacity()) {
                listNumber.add(addedSymbol)
            } else cursorPosition--
        }
    }

    private fun isNotMoreThanDigitCapacity(): Boolean {
        if (isAddedComma && digitCapacity > -1) {
            val indexSeparator = getIndexSeparator()
            if (cursorPosition > indexSeparator + 1) {
                val isMore = digitCapacity > listNumber.size - indexSeparator - 1
                if (!isMore) listener(editText.text.toString(), ErrorMessage.DIGIT_CAPACITY)
                return isMore
            }

        }
        return true
    }

    private fun getIndexSeparator(): Int {
        return listNumber.indexOf(separator.char)
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
        return if (withPostfix) "$text $postfix" else text
    }
}

enum class Separator(val char: String) {
    DOT("."), COMMA(",")
}

/**
 *
 */
enum class ErrorMessage {
    HAS_FOCUS, MINIMUM, MAXIMUM, DIGIT_CAPACITY, CHANGED
}