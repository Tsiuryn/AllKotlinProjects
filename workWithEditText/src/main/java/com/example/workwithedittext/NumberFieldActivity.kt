package com.example.workwithedittext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.DigitsKeyListener
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.workwithedittext.number.NumberControl
import com.example.workwithedittext.number.Separator
import com.google.android.material.textfield.TextInputLayout

class NumberFieldActivity : AppCompatActivity() {

    private lateinit var vInputLayout: TextInputLayout
    private lateinit var vEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number_field)
        vInputLayout = findViewById(R.id.vInputLayout)
        vEditText = vInputLayout.editText!!
        val number = NumberControl(
            editText = vEditText,
            defaultValue = "15.0",
            postfix = "BYN",
            fraction = false,
            digitCapacity = 10,
            supplDigitCapacity = true,
            separator = Separator.DOT,
            maxValue = 250,
            minValue = 10
        ){value, errorMessage ->
            Log.d("TAG11", "onCreate: $value, $errorMessage")
        }
        findViewById<Button>(R.id.button1).setOnClickListener{
        }


    }
}