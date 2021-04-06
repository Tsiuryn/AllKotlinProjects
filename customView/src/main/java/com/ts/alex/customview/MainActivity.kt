package com.ts.alex.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ts.alex.customview.view.TriangleModel
import com.ts.alex.customview.view.TriangleView
import com.ts.alex.customview.view.ViewListener

class MainActivity : AppCompatActivity(), ViewListener {
    private lateinit var triangle: TriangleView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        triangle = findViewById(R.id.vTriangle)
        triangle.setListener(this)


    }

    override fun onClick(model: TriangleModel) {
        Toast.makeText(this, "${model.text}", Toast.LENGTH_SHORT).show()
    }

    override fun onLongClick() {

    }
}