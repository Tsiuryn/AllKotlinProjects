package com.example.addbuttonprogr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.addbuttonprogr.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var count = 0
    private val buttonId = 5000
    private val textId = 6000

    private lateinit var binding: ActivityMainBinding
    private lateinit var linearLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        linearLayout = binding.myLinear
        binding.myButton.setOnClickListener {
            addButton()
        }
    }

    private fun addButton() {
        val button = Button(this)
        button.text = "Удалить эту кнопку ${count + 1}"

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.marginEnd = 50
        params.marginStart = 50
        button.layoutParams = params
        button.id = buttonId + count
        val text = addText()

        button.setOnClickListener{
            linearLayout.removeView(it)
            linearLayout.removeView(text)
        }
        linearLayout.addView(button)
        count++


    }

    private fun addText (): EditText{
        val editText = EditText(this)
        editText.hint = "hello"
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.marginStart = 60
        params.marginEnd = 60
        editText.id = textId + count
        editText.layoutParams = params
        linearLayout.addView(editText)
        return editText
    }

    private fun layout(){
        val layout = ConstraintLayout(this)
    }
}