package com.example.workwithedittext.mask

import android.util.Log
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText

data class CharsMaskResult(
    var symbol: String,
    val rool: String,

    )

val mask = "+375 (2\\9)999 99 99"

class MaskEditText(private val editText: TextInputEditText, private val mask: String) {
    private var textBefore = ""
    private var textAfter = ""
    private var cursorPosition = 0
    private var listMask = ArrayList<CharsMaskResult>()

    init {
        validateEditTextByMask()
    }

    private fun validateEditTextByMask() {
        var isBackSpace: Boolean
        var ignore = false
        listMask = startProgram(mask)
        editText.setText(getSymbolsFromMask())

        editText.doOnTextChanged { text, start, before, count ->
            isBackSpace = before > count
            if (ignore) return@doOnTextChanged
            ignore = true
            cursorPosition = editText.selectionStart
            var addedSymbol = ""
            textAfter = editText.text.toString()
            if (isBackSpace) {
                setBackSpace()
            } else {
                addedSymbol = text!!.substring(cursorPosition - 1, cursorPosition)
                textBefore = "${text.substring(0, cursorPosition - 1)}${
                    text.substring(
                        cursorPosition,
                        textAfter.length
                    )
                }"
                addSymbol(
                    addedSymbol = addedSymbol
                )
            }

            Log.d(
                "TAG11",
                "validateEditTextByMask: $textBefore, $textAfter $addedSymbol $cursorPosition"
            )

            editText.setText(getSymbolsFromMask())
            editText.setSelection(cursorPosition)
            ignore = false
        }
    }

    private fun getSymbolsFromMask(): String {
        var updatedMask = ""
        for (item in listMask) {
            updatedMask += item.symbol
        }
        return updatedMask
    }

    private fun startProgram(mask: String): ArrayList<CharsMaskResult> {
        val list = arrayListOf<CharsMaskResult>()
        val maskArray = mask.toCharArray()
        var position = 0
        for (i in maskArray.indices) {
            when (maskArray[i]) {
                '9' -> {
                    if (i > 0) {
                        if (maskArray[i - 1] == '\\') {
                            list.add(CharsMaskResult(symbol = "9", rool = "i"))
                        } else {
                            list.add(CharsMaskResult(symbol = "_", rool = "9"))
                        }
                    } else {
                        list.add(CharsMaskResult(symbol = "_", rool = "9"))
                    }
                }
                'a' -> {
                    if (i > 0) {
                        if (maskArray[i - 1] == '\\') {
                            list.add(CharsMaskResult(symbol = "a", rool = "i"))
                        } else {
                            list.add(CharsMaskResult(symbol = "_", rool = "a"))
                        }
                    } else {
                        list.add(CharsMaskResult(symbol = "_", rool = "a"))
                    }
                }
                '*' -> {
                    if (i > 0) {
                        if (maskArray[i - 1] == '\\') {
                            list.add(CharsMaskResult(symbol = "*", rool = "i"))
                        } else {
                            list.add(CharsMaskResult(symbol = "_", rool = "*"))
                        }
                    } else {
                        list.add(CharsMaskResult(symbol = "_", rool = "*"))
                    }
                }
                '\\' -> {
                    Log.d("TAG11", "startProgram: ${maskArray[i]}")
                    continue
                }
                else -> {
                    list.add(CharsMaskResult(symbol = maskArray[i].toString(), rool = "i"))
                }
            }
        }
        return list
    }

    private fun addSymbol(addedSymbol: String) {
        when {
            cursorPosition > listMask.size -> cursorPosition = getFirstAddedPosition()
            listMask[cursorPosition - 1].rool == "i" -> cursorPosition = getFirstAddedPosition()
            else -> {
                if (isValidAddingSymbol(addedSymbol = addedSymbol )) {
                    val charsMaskResult = listMask[cursorPosition - 1]
                    listMask[cursorPosition - 1] = CharsMaskResult(
                        symbol = addedSymbol,
                        rool = charsMaskResult.rool,
                        )
                    cursorPosition = getFirstAddedPosition()
                }else {
                    cursorPosition --
                }

            }
        }
    }

    private fun isValidAddingSymbol(addedSymbol: String): Boolean {
        return when (listMask[cursorPosition - 1].rool) {
            "9" -> addedSymbol.matches(Regex("[0-9]"))
            "a" -> addedSymbol.matches(Regex("[a-zA-Z]"))
            "*" -> addedSymbol.matches(Regex("[a-zA-Z0-9]"))
            else -> false
        }
    }

    private fun getFirstAddedPosition(): Int {
        var position = -1
        for (i in listMask.size - 1 downTo  0) {
            if (listMask[i].rool != "i" && listMask[i].symbol == "_") {
                position = i
            }
        }
        return if(position < listMask.size && position != -1) position else listMask.size
    }

    private fun getLastAddedPosition(): Int {
        var firstAddedSymbol = -1
        for (i in listMask.size - 1 downTo  0) {
            if (listMask[i].rool != "i"){
                if(listMask[i].symbol == "_") {
                    firstAddedSymbol =  i
                } else {
                    return i + 1
                }
            }
        }
        return firstAddedSymbol
    }

    private fun setBackSpace(
    ) {
        val rool = listMask[cursorPosition].rool
        if(rool != "i"){
            listMask[cursorPosition] = CharsMaskResult(
                symbol = "_",
                rool = rool
            )
        } else{
            listMask[getLastAddedPosition()] = CharsMaskResult(
                symbol = "_",
                rool =  listMask[getLastAddedPosition() - 1].rool
            )
        }
        cursorPosition = getLastAddedPosition()
    }
}