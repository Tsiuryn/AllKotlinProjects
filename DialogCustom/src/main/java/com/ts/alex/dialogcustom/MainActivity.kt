package com.ts.alex.dialogcustom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.ts.alex.dialogcustom.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), onClickItem {
    companion object{
        const val FROM_ACTIVITY_TO_FRAGMENT = "FROM_ACTIVITY_TO_FRAGMENT"
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val list = arrayListOf<String>()
        (0 until 100).forEach { i ->
            list.add(i.toString())
        }
        binding.vButton.setOnClickListener{
            val fm: FragmentManager = supportFragmentManager
            val custom = CustomDialogFragment(this)
            val bundle = Bundle()
            bundle.putStringArrayList(FROM_ACTIVITY_TO_FRAGMENT, list)
            custom.arguments = bundle
            custom.show(fm, "")
        }
    }

    override fun onClick(text: String) {
        binding.vResult.text = text
    }
}