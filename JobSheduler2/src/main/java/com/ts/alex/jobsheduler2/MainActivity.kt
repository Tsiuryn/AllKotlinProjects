package com.ts.alex.jobsheduler2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var editTextInput: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editTextInput = findViewById(R.id.edit_text_input)
    }

    fun enqueueWork(v: View) {
        val input: String = editTextInput.getText().toString()
        val serviceIntent = Intent(
            this,
            ExampleJobIntentService::class.java
        )
        serviceIntent.putExtra("inputExtra", input)
        ExampleJobIntentService.enqueueWork(this, serviceIntent)
    }
}