package com.example.parserxml

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class ActivityViewModel: ViewModel() {

    private val url = "https://news.tut.by/rss/"
    private val fullUrl = "https://news.tut.by/rss/economics.rss"

    private var _getXml = MutableLiveData<String>()
    val getXml: LiveData<String>
    get() = _getXml



    fun getXML(){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                val response = getBody()
                withContext(Dispatchers.Main){
                    _getXml.value = response
                }
            }


        }
    }

    suspend fun getBody(): String?{
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(fullUrl)
            .build()
        return client.newCall(request).execute().body?.string()
    }






    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            connectTimeout(60, TimeUnit.SECONDS)
            addInterceptor(getLoggingInterceptor())
        }.build()
    }

//    private fun provideRestApi(
//        baseUrl: String,
//        okHttpClient: OkHttpClient
//    ): Placeholder {
//        val retrofit = Retrofit.Builder().apply {
//            client(okHttpClient)
//            addConverterFactory(SimpleXmlConverterFactory.create())
//            baseUrl(baseUrl)
//        }.build()
//
//        return retrofit.create()
//    }

    private fun getLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }



}