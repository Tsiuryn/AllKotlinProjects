package com.example.parserxml

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.parserxml.entity.News
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "TAG11"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel = ViewModelProvider(this).get(ActivityViewModel::class.java)
        viewModel.getXML()
        viewModel.apply {
            getXml.observe(this@MainActivity, Observer {
//                Log.d(TAG, "$it")
//                findViewById<TextView>(R.id.xmlText).text = it
                showAllList(parseXML(it))
//                parseXml33(it)
            })
        }

//        val parser = createXmlPullParser(XMLText)
//        val list = parser?.let { parseXML2(it) }
//        Log.d(TAG, "onCreate: $list")
    }

    private fun parseXML(text: String): List<News> {
        val factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = true
        val xpp = factory.newPullParser()
        xpp.setInput(StringReader(text))
        xpp.next()
        var eventType = xpp.eventType
        val allNews = arrayListOf<News>()
        var isValidText = false
        var title = ""
        var description = ""
        var iconUrl  = ""
        var link = ""
        var pubDate = ""
        while (eventType != XmlPullParser.END_DOCUMENT) {


            if (eventType == XmlPullParser.START_TAG) {

                if (xpp.name == "item") {
                    isValidText = true
                }

                when (true) {
                    isValidText && xpp.name.equals("title" ) -> {
//                        Log.d(TAG, "title: ${xpp.nextText()}")
                        title = xpp.nextText()
                    }
                    isValidText && xpp.name == "description" -> {
//                        Log.d(TAG, "description: ${xpp.nextText()}")

                        description = xpp.nextText()

                    }
                    isValidText && xpp.name == "content"-> {
//                        Log.d(TAG, "enclosure: ${xpp.getAttributeValue(0)}")

                        iconUrl = xpp.getAttributeValue(null, "url")
                    }
                    isValidText && xpp.name == "pubDate" -> {
//                        Log.d(TAG, "pubDate: ${xpp.nextText()}")
                        pubDate = xpp.nextText()
                    }
                    isValidText && xpp.name == "link" -> {
//                        Log.d(TAG, "pubDate: ${xpp.nextText()}")
                        link = xpp.nextText()
                    }

                }

            } else if (eventType == XmlPullParser.END_TAG) {
                val name = xpp.name
                if (name.equals("item", true)) {
                    isValidText = false
                    if (title.isNotEmpty()){
                        allNews.add(News(title, iconUrl, description, pubDate, link))
                    }
                    title = ""
                    description = ""
                    iconUrl = ""
                    pubDate = ""
                    link = ""

                }
            }

            eventType = xpp.next()

        }
//        Log.d(TAG, "parseXML: $allNews")
        return  allNews
    }

    private fun showAllList(list: List<News>){
        list.forEach{
            Log.d(TAG, "showAllList: $it")
        }

    }

    private fun parseXml33(text: String){
        val factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = true
        val xpp = factory.newPullParser()
        xpp.setInput(StringReader(text))
        xpp.next()
        var eventType = xpp.eventType
        while (eventType != XmlPullParser.END_DOCUMENT){
            if(eventType == XmlPullParser.TEXT){
                Log.d(TAG, "parseXml33: ${xpp.name}")
            }
            xpp.next()
        }
    }

}