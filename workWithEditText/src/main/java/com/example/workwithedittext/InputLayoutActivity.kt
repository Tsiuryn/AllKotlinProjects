package com.example.workwithedittext

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.workwithedittext.databinding.ActivityInputLayoutBinding

class InputLayoutActivity: AppCompatActivity() {
    private lateinit var binding: ActivityInputLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Toast.makeText(this, "sdfadsfasdf", Toast.LENGTH_SHORT).show()
        Log.d("TAG1", "onCreate: sdlkfj")
        binding.vTextInputLayout1.editText!!.doAfterTextChanged {

        }
    }
}