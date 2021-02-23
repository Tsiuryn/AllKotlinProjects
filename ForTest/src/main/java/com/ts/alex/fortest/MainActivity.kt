package com.ts.alex.fortest

import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.vFileName)
//        editText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_get_app_24, 0, 0, 0)

        val inputLayout = findViewById<TextInputLayout>(R.id.vContainer)
        inputLayout.orientation = LinearLayout.HORIZONTAL



    }
}