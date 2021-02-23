package com.ts.alex.fortest

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val editText = findViewById<EditText>(R.id.vFileName)
//        editText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_get_app_24, 0, 0, 0)

//        val inputLayout = findViewById<TextInputLayout>(R.id.vContainer)
//        inputLayout.orientation = LinearLayout.HORIZONTAL

        val text = findViewById<EditText>(R.id.vText)
//        val icon = text.background as GradientDrawable
//        icon.setColor(Color.RED)

        text.setBackgroundResource(R.drawable.input_layout)
        val gd = text.background.current as GradientDrawable
        gd.setStroke(2, Color.RED, 0f, 6f)
//        gd.setColor(Color.parseColor("#000000"))
//        gd.cornerRadii = floatArrayOf(30f, 30f, 30f, 30f, 0f, 0f, 30f, 30f)




    }
}