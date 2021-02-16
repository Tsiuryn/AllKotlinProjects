package com.example.workwithedittext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import com.example.workwithedittext.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

//

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        const val TAG = "TAG1"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var isBackSpace = false
        var ignore = false
        binding.myEditText.doOnTextChanged { text, start, before, count ->
            isBackSpace = before > count
            if (ignore) return@doOnTextChanged
            ignore = true
//            binding.myEditText.checkTextBySymbol(isBackSpace)
//            binding.myEditText.checkTextByLength(3, isBackSpace)
            binding.myEditText.replaceTexBySpecified(" Хорошего настроения Вам в Новом году!", isBackSpace)
            ignore = false
        }

        binding.imageButton.contentDescription = "sldkjflsjdfsdkf"

    }
}