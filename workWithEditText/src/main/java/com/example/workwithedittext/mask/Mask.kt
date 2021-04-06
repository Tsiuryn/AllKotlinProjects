package com.example.workwithedittext.mask

import android.util.Log
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText

data class CharsMaskResult(
    val symbol: Char,
    val rool: Char,
    val number: Int
)

val mask = "+375 (2\\9)999 99 99"

fun checkEditTextWithMask(editText: TextInputEditText, mask: String) {
    var isBackSpace = false
    var ignore = false
    var updatedMask = ""
    for (item in startProgram(mask)) {
        updatedMask += item.symbol
    }
    Log.d(
        "TAG1",
        "checkEditTextWithMask: ${startProgram(mask)}"
    )
    editText.setText(updatedMask)
    editText.doBeforeTextChanged { text, start, before, count ->
        isBackSpace = before > count
        if (ignore) return@doBeforeTextChanged
        ignore = true
        Log.d(
            "TAG1",
            "checkEditTextWithMask: text $text start $start, before $before, count $count "
        )
        validationText(
            isBackSpace = isBackSpace,
            textBefore = text.toString(),
            textAfter = editText.text.toString(),
            cursorPosition = editText.selectionStart
        )

        ignore = false
    }
}

private fun startProgram(mask: String): List<CharsMaskResult> {
    val list = arrayListOf<CharsMaskResult>()
    val maskArray = mask.toCharArray()
    var position = 0
    for (i in maskArray.indices) {
        when (maskArray[i]) {
            '9' -> {
                if (i > 1) {
                    if (maskArray[i - 1] == '\\' && maskArray[i - 2] == '\\') {
                        list.add(CharsMaskResult(symbol = '9', rool = 'i', number = position++))
                    } else {
                        list.add(CharsMaskResult(symbol = '_', rool = '9', number = position++))
                    }
                }
            }
            'a' -> {
                if (i > 1) {
                    if (maskArray[i - 1] == '\\' && maskArray[i - 2] == '\\') {
                        list.add(CharsMaskResult(symbol = 'a', rool = 'i', number = position++))
                    } else {
                        list.add(CharsMaskResult(symbol = '_', rool = 'a', number = position++))
                    }
                }
            }
            '*' -> {
                if (i > 1) {
                    if (maskArray[i - 1] == '\\' && maskArray[i - 2] == '\\') {
                        list.add(CharsMaskResult(symbol = '*', rool = 'i', number = position++))
                    } else {
                        list.add(CharsMaskResult(symbol = '_', rool = '*', number = position++))
                    }
                }
            }
            '\\' -> continue
            else -> {
                list.add(CharsMaskResult(symbol = maskArray[i], rool = 'i', number = position++))
            }
        }
    }
    return list
}

private fun validationText(
    isBackSpace: Boolean,
    textBefore: String,
    textAfter: String,
    cursorPosition: Int
): String {
    if (isBackSpace) {

    } else {
        val arrayChar = textAfter.toCharArray()
        val lastChar = arrayChar[arrayChar.size - 1]

    }
    return ""
}

