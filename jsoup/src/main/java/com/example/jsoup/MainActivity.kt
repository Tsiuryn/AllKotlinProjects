package com.example.jsoup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class MainActivity : AppCompatActivity() {
    private lateinit var text: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        text = findViewById(R.id.myText)
        findViewById<Button>(R.id.myButton).setOnClickListener{
            Log.d("TAG1", "onCreate: ")
            getResponse()
        }

    }

    private fun getResponse (){
        var myText = ""
        GlobalScope.launch(Dispatchers.IO){
            val doc = Jsoup.connect("https://myfin.by/currency/minsk").get()
            val contents = doc.select(".col-xs-12")
//            val myContents = contents[0].children()
            withContext(Dispatchers.Main){
                Log.d("TAG1", "getResponse: ")
                for (content in contents){
                    myText += content.text()
                }
                text.text = contents.text()
            }
        }
    }

//    private fun getResponse (){
//        var myText = ""
//        GlobalScope.launch(Dispatchers.IO){
//            val doc = Jsoup.connect("https://www.tut.by/").get()
//            val contents = doc.select(".b-pod")
//            withContext(Dispatchers.Main){
//                for (content in contents){
//                    myText += content.text()
//                }
//                text.text = myText
//            }
//        }
//    }
}

