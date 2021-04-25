package com.ts.alex.objectbox

import android.content.Context
import io.objectbox.BoxStore

object NewsDataBase {

    private lateinit var boxStore: BoxStore
    fun init(context: Context) {
        boxStore = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()


    }

    fun get() = boxStore
}
