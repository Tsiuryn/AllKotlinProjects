package com.example.jobscheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.evernote.android.job.JobManager
import com.evernote.android.job.JobRequest
import com.example.jobscheduler.databinding.ActivityMainBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private var jobId = -1

    private lateinit var binging: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binging = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binging.root)
        val sec = 40
        binging.startJob.setOnClickListener {

//            scheduleJob(this, sec)  // start job sheduler
            scheduleExactJob(sec)
            Log.d(TAG, "onCreate: $jobId")
            timer(sec)
        }
        binging.cancelJob.setOnClickListener {
//            cancelJob()
            cancelJob(jobId)
        }
    }

    private fun timer(sec: Int){
        var mySec = sec
        object : CountDownTimer(sec * 1000L, 5_000){
            override fun onTick(millisUntilFinished: Long) {

                Toast.makeText(this@MainActivity, "$mySec", Toast.LENGTH_SHORT).show()
                mySec -= 5
            }

            override fun onFinish() {
                Toast.makeText(this@MainActivity, "onFinish", Toast.LENGTH_SHORT).show()
            }

        }.start()
    }

    private fun scheduleExactJob(sec: Int) {
        jobId = JobRequest.Builder(DemoSyncJob.TAG)
            .setExact(sec * 1000L)
            .build()
            .schedule()
    }

    private fun cancelJob(jobId: Int) {
        JobManager.instance().cancel(jobId)
    }



    private fun scheduleJob(context: Context, sec: Int) {
        val componentName = ComponentName(this, ExampleJobService::class.java)
        val info = JobInfo.Builder(123, componentName)
            .setRequiresCharging(true)
            .setOverrideDeadline(sec * 1000L)

//            .setPeriodic(sec * 1000L)
//            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
            .setPersisted(true)
            .build()

        val myScheduler =
            getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        myScheduler.schedule(info)

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
        super.onStop()
    }
}