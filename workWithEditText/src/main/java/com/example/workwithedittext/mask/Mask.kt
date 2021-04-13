package com.example.workwithedittext.mask

import android.text.InputType
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText

data class CharsMaskResult(
    var symbol: String,
    val rool: String,

    )

class MaskEditText(
    private val editText: TextInputEditText,
    private val mask: String,
    private val value: String? = null
) {
    private var cursorPosition = 0
    private var listMask = ArrayList<CharsMaskResult>()

    fun getValue(): String {
        var textValue = ""

        if (listMask.isNotEmpty()) {
            for (i in 0 until listMask.size) {
                if (listMask[i].symbol.matches(Regex("[a-zA-Z0-9]"))) {
                    textValue += listMask[i].symbol
                }
            }
        }
        return textValue
    }

    init {
        validateEditTextByMask()
    }

    private fun validateEditTextByMask() {
        var isBackSpace: Boolean
        var ignore = false
        listMask = startProgram(mask)
        updateListMaskByValue()
        editText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        editText.setText(getSymbolsFromMask())

        editText.doOnTextChanged { text, start, before, count ->
            isBackSpace = before > count
            if (ignore) return@doOnTextChanged
            ignore = true
            cursorPosition = editText.selectionStart
            var addedSymbol = ""
            if (isBackSpace) {
                backSpace()
            } else {
                addedSymbol = text!!.substring(cursorPosition - 1, cursorPosition)
                addSymbol(
                    addedSymbol = addedSymbol
                )
            }
            editText.setText(getSymbolsFromMask())
            editText.setSelection(cursorPosition)
            ignore = false
        }
    }

    private fun updateListMaskByValue() {
        if (!value.isNullOrEmpty()) {
            val valueArray = value.toCharArray()
            var indexValue = 0
            for (i in 0 until listMask.size) {
                val charsMaskResult = listMask[i]
                if (charsMaskResult.symbol.matches(Regex("[a-zA-Z0-9_]"))) {
                    if (indexValue < valueArray.size) {
                        listMask[i] = CharsMaskResult(
                            symbol = valueArray[indexValue].toString(),
                            rool = charsMaskResult.rool
                        )
                        indexValue++
                    }
                }
            }
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
        if(cursorPosition <= listMask.size){
            for (i in 0 until listMask.size) {
                if (listMask[i].rool != "i" && listMask[i].symbol == "_") {
                    if (isValidAddingSymbol(addedSymbol, i)) {
                        val charsMaskResult = listMask[i]
                        listMask[i] = CharsMaskResult(
                            symbol = addedSymbol,
                            rool = charsMaskResult.rool,
                        )
                        cursorPosition = getFirstAddedPosition()
                        break
                    } else {
                        cursorPosition = getFirstAddedPosition()
                        break
                    }
                }
            }
        }else {
            cursorPosition = getFirstAddedPosition()
        }

    }

    private fun backSpace() {
        var isHaveAddedSymbol: Boolean = false
        for (i in listMask.size - 1 downTo 0) {
            if (listMask[i].rool != "i" && listMask[i].symbol != "_") {
                listMask[i] = CharsMaskResult(
                    symbol = "_",
                    rool = listMask[i].rool
                )
                cursorPosition = i
                isHaveAddedSymbol = true
                break
            }
        }
        if (!isHaveAddedSymbol) {
            cursorPosition = getFirstAddedPosition()
        }
    }

    private fun isValidAddingSymbol(addedSymbol: String, index: Int): Boolean {
        return when (listMask[index].rool) {
            "9" -> addedSymbol.matches(Regex("[0-9]"))
            "a" -> addedSymbol.matches(Regex("[a-zA-Z]"))
            "*" -> addedSymbol.matches(Regex("[a-zA-Z0-9]"))
            else -> false
        }
    }

    private fun getFirstAddedPosition(): Int {
        var position = -1
        for (i in listMask.size - 1 downTo 0) {
            if (listMask[i].rool != "i" && listMask[i].symbol == "_") {
                position = i
            }
        }
        return if (position < listMask.size && position != -1) position else listMask.size
    }

}