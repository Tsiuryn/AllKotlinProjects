package com.ts.alex.fortest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class BrowserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser)

        findViewById<Button>(R.id.vButton).setOnClickListener{
            openBrowser()
        }
    }

    private fun openBrowser() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("poc://deeplink.flutter.dev")))
    }


}