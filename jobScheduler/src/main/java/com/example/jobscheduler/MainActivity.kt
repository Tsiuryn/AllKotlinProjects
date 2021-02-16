package com.example.jobscheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.icu.util.TimeUnit
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.jobscheduler.databinding.ActivityMainBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.dialog.MaterialDialogs
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var binging: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binging = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binging.root)
        binging.startJob.setOnClickListener {
            scheduleJob(this)
            showToast("start")
//            showDate()
        }
        binging.cancelJob.setOnClickListener {
            cancelJob()
            showToast("cancel")
        }
    }

    private fun showToast(text: String) {
        val view = layoutInflater.inflate(R.layout.layout_dialog, null)

//        val toast = Toast(this)
//        toast.view = view
//        toast.duration = Toast.LENGTH_LONG
//        toast.setGravity(Gravity.TOP, 0, 0)
//        toast.show()

        val contextView = findViewById<View>(R.id.layout)
        val snack = Snackbar.make(contextView, "", Snackbar.LENGTH_LONG)
        val snackView = snack.view
        val params = snackView.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        snackView.layoutParams = params
        snack.show()

    }

    private fun scheduleJob(context: Context) {
        val componentName = ComponentName(this, ExampleJobService::class.java)
        val info = JobInfo.Builder(123, componentName)
            .setRequiresCharging(true)
//            .setOverrideDeadline(10000)
            .setPeriodic(40*1000)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
            .setPersisted(true)

        val myScheduler =
            context.getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        myScheduler.schedule(info.build())

    }

    private fun showDialog (){
        val dialobBuilder = AlertDialog.Builder(this)
            .setTitle("Custom dialog")

        val view = layoutInflater.inflate(R.layout.layout_dialog, null) as LinearLayout

        dialobBuilder.setView(view)
        dialobBuilder.create().show()
    }

    private fun cancelJob() {
        val scheduler: JobScheduler =
            getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        scheduler.cancel(123)
        Log.d(TAG, "Job cancelled")
    }

    private fun showTime(mls: Long) {
        val materialTimePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setTitleText("Choose the time")
            .build()

        materialTimePicker.addOnPositiveButtonClickListener {
            val hours = materialTimePicker.hour
            val minute = materialTimePicker.minute
            val milis = hours  * 60 * 60 * 1000 + minute * 60 * 1000 + mls

            showToast(convertTime(milis))
        }
        materialTimePicker.show(this.supportFragmentManager, "fragment_tag")


    }

    private fun convertTime(mls: Long): String {
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        return formatter.format(mls)
    }

    private fun showDate() {
        val materialDatePicker = MaterialDatePicker.Builder.datePicker()
        materialDatePicker.setTitleText("Select a Date")
        val material = materialDatePicker.build()

        material.show(this.supportFragmentManager, "DATE_PICKER")
        material.addOnPositiveButtonClickListener {
            showTime(it)
        }
    }

    override fun onStop() {
        Log.d(TAG, "onStop: ")
        showToast("Bue!")
        super.onStop()
    }
}