package com.example.workwithedittext

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.workwithedittext.databinding.ActivityMaskBinding
import com.example.workwithedittext.mask.MaskEditText
import com.google.android.material.textfield.TextInputEditText

class MaskActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMaskBinding

    private lateinit var vEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaskBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        vEditText = binding.vEditText
//        val mask = "+375 (2\\9)999 99 99"
        val mask = "+375 (aa) aaa a\\a aa"
        MaskEditText(vEditText, mask)


    }
}