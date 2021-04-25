package com.ts.alex.objectbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val box = NewsDataBase.get().boxFor(MyEnt::class.java)
        val editText = findViewById<EditText>(R.id.vInputText)
        val textView = findViewById<TextView>(R.id.vText)
        findViewById<Button>(R.id.vAddToDB).setOnClickListener{

            val myEnt2 = MyEnt(name = editText.text.toString())
            val another = Another(data = getCurrentTime())
            myEnt2.another?.target = another

            box.put(myEnt2)

            val myEnt = box.all
            var text = ""
            myEnt.forEach{
                text += "${it.name}  ${it.another?.target!!.data}\n"
            }
            textView.text = text

        }
        findViewById<Button>(R.id.vRemoveDB).setOnClickListener{
            box.removeAll()
        }
    }

    private fun getCurrentTime():String{
        val format = SimpleDateFormat("DD-MMM-yyyy hh_mm_ss", Locale.getDefault())
        val calendar = Calendar.getInstance().time
        return format.format(calendar)

    }
}