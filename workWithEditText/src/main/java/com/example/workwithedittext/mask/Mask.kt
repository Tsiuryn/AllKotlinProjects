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

class MaskEditText(private val editText: TextInputEditText, private val mask: String) {
    private var textBefore = ""
    private var textAfter = ""
    private var cursorPosition = 0
    init {
        validateEditTextByMask()
    }

    private fun validateEditTextByMask() {
        var isBackSpace = false
        var ignore = false
        var updatedMask = ""
        val listMask = startProgram(mask)
        for (item in listMask) {
            updatedMask += item.symbol
        }

        editText.setText(updatedMask)
        editText.doBeforeTextChanged {text, start, before, count ->
            if (ignore) return@doBeforeTextChanged
            textBefore = text.toString()
        }
        editText.doOnTextChanged { text, start, before, count ->
            isBackSpace = before > count
            if (ignore) return@doOnTextChanged
            ignore = true
            cursorPosition = editText.selectionStart
            var addedSymbol = ""
            textAfter = editText.text.toString()
            if(isBackSpace){

            }else{
                addedSymbol = text!!.substring(cursorPosition-1, cursorPosition)
                textBefore = "${text.substring(0, cursorPosition - 1)}${text.substring(cursorPosition-1, textAfter.length)}"
            }

            Log.d("TAG11", "validateEditTextByMask: $textBefore, $textAfter $addedSymbol")
//        val result = validationText(
//            isBackSpace = isBackSpace,
//            listMask = listMask
//        )
//        editText.setText(result.text)
//        editText.setSelection(result.cursorPosition)
//            ignore = false
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
                        if (maskArray[i - 1] == '\\') {
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
        listMask: List<CharsMaskResult>
    ): Result {
        if (isBackSpace) {
            return setBackSpace(
                listMask = listMask
            )

        } else {
            return addChar(
                listMask = listMask
            )
            val arrayChar = textAfter.toCharArray()

        }
        return Result("", 0)
    }

    fun addChar(listMask: List<CharsMaskResult>): Result {
        return  if(cursorPosition >= listMask.size){
            Result(
                text = textBefore,
                cursorPosition = cursorPosition
            )
        } else {
            val addedChar = textAfter.substring(cursorPosition -1 , cursorPosition)
            Log.d("TAG1", "addChar: $addedChar")
            return Result(textAfter, cursorPosition)
        }
    }

    private fun setBackSpace(
        listMask: List<CharsMaskResult>
    ): Result {
        val deletedChar = deletedSymbol(textBefore = textBefore, cursorPosition = cursorPosition)

        val roolChar = listMask[cursorPosition - 1].rool
        Log.d("TAG1", "setBackSpace: $cursorPosition $textBefore $deletedChar $roolChar")

        return when (roolChar){
            'i' -> Result(text = textBefore, cursorPosition = cursorPosition )
            'a' -> Result(text = "$textBefore", cursorPosition = cursorPosition - 1 )
            '*' -> Result(text = "$textBefore", cursorPosition = cursorPosition - 1 )
            '9' -> Result(text = "$textBefore", cursorPosition = cursorPosition - 1 )
            else -> Result(text = "", cursorPosition = 0)
        }
    }

    private fun deletedSymbol (textBefore: String, cursorPosition: Int): String{
        return if(cursorPosition > 1 ){
            textBefore.substring(cursorPosition-1, textBefore.length)
        } else {
            textBefore
        }
    }

}

data class Result(
    val text: String,
    val cursorPosition: Int
)

