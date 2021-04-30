package com.ts.alex.dialogcustom

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
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

        val listener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            Log.e("AAQQ", "test $year/$month/$dayOfMonth")
        }

        val listener2 = TimePickerDialog.OnTimeSetListener{view, hour, minute ->

            Log.e("AAQQ", "test $hour : $minute")
        }


        binding.vButton.setOnClickListener{
//            val dialog = DatePickerDialog(this, listener, 2020, 10, 11)
//            dialog.show()
//            DateDialog().show(supportFragmentManager, "DateDialog")

            TimePickerDialog(this, listener2, 22, 33, true).show()
        }






//        val list = arrayListOf<String>()
//        (0 until 100).forEach { i ->
//            list.add("name_$i")
//        }
//        binding.vButton.setOnClickListener{
//            val fm: FragmentManager = supportFragmentManager
//            val custom = CustomDialogFragment(this)
//            val bundle = Bundle()
//            bundle.putStringArrayList(FROM_ACTIVITY_TO_FRAGMENT, list)
//            custom.arguments = bundle
//            custom.show(fm, "")
//        }
    }

    private fun showDialogPicker() {
        DateDialog().show(supportFragmentManager, "datePicker")
    }

    override fun onClick(text: String) {
        binding.vResult.text = text
    }
}