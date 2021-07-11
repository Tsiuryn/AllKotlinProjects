package com.ts.alex.fortest

import java.util.*

fun main() {

    val list = mutableListOf<String>()
    for (i in 0 until 100){
        list.add(UUID.randomUUID().toString())
        println(list[i])
    }
}