package com.example.workwithedittext

import android.annotation.SuppressLint
import android.util.Log
import android.widget.EditText
import com.example.workwithedittext.MainActivity.Companion.TAG

@SuppressLint("SetTextI18n")
fun EditText.checkTextBySymbol(isBackSpace: Boolean) {
    val sourceText = this.text.toString()
    val currentCursor = this.selectionStart
    val addedSymbol = if (!isBackSpace) {
        sourceText.substring(currentCursor - 1, currentCursor)
    } else ""
    if (addedSymbol.isNotEmpty() && !addedSymbol.validSymbol()) {
        val left = sourceText.substring(0, currentCursor - 1)
        val right = sourceText.substring(currentCursor, sourceText.length)
        this.setText("$left$right")
        this.setSelection(currentCursor - 1)
    }
}

private fun String.validSymbol(): Boolean {
    return this.matches(Regex("[A-Za-z]"))
//    return this.matches(Regex("[0-9]"))
}

fun EditText.checkTextByLength(maxLength: Int, isBackSpace: Boolean) {
    val sourceText = this.text.toString()
    val currentCursor = this.selectionStart
    val addedSymbol = if (!isBackSpace) {
        sourceText.substring(currentCursor - 1, currentCursor)
    } else ""
    if (addedSymbol.isNotEmpty() && sourceText.length >= maxLength + 1) {
        val left = sourceText.substring(0, currentCursor - 1)
        val right = sourceText.substring(currentCursor, sourceText.length)
        this.setText("$left$right")
        this.setSelection(currentCursor - 1)
    }
}

fun EditText.replaceTexBySpecified (newText: String, isBackSpace: Boolean) {
    val sourceText = this.text.toString()
    val currentCursor = this.selectionStart
    val addedSymbol = if (!isBackSpace) {
        sourceText.substring(currentCursor - 1, currentCursor)
    } else ""
    if (addedSymbol.isNotEmpty() && currentCursor <= newText.length) {
        val left = sourceText.substring(0, currentCursor - 1)
        var right = sourceText.substring(currentCursor, sourceText.length)
        if(right.isNotEmpty()){
            right = right.substring(1, right.length)
        }
        val newSymbol = newText.toCharArray()[currentCursor - 1]
        this.setText("$left$newSymbol$right")
        this.setSelection(currentCursor)
    } else if(addedSymbol.isNotEmpty()){
        val left = sourceText.substring(0, currentCursor - 1)
        val right = sourceText.substring(currentCursor, sourceText.length)
        this.setText("$left$right")
        this.setSelection(currentCursor-1)
    }
}

private fun crash(){
    var a = 1
    while (true) a++
}

