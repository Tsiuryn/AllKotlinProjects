package com.example.jobscheduler

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExampleJobService : JobService() {
    private val TAG = "TAG1"
    private var jobCanceled = false

    override fun onStartJob(params: JobParameters?): Boolean {
            doBackgroundWork(params)
        Log.d(TAG, "onStartJob: start")
        return true
    }

    private fun doBackgroundWork(params: JobParameters?) {
        GlobalScope.launch(Dispatchers.IO) {
            for (i in 0..9) {
                Log.d(TAG, "run: $i")
                if (jobCanceled) {
                    return@launch
                }

                try {
                    delay(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            Log.d(TAG, "Job finished")
            jobFinished(params, false)

        }
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d(TAG, "Job cancelled before completion")
        jobCanceled = true
        return false
    }
}