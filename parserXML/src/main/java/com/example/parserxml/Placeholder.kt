package com.example.parserxml

import retrofit2.http.GET

interface Placeholder {

    @GET("economics.rss")
    suspend fun getXML(): String
}