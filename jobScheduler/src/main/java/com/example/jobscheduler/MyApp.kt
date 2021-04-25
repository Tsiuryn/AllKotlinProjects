package com.example.jobscheduler

import android.app.Application
import android.util.Log
import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator
import com.evernote.android.job.JobManager
import com.evernote.android.job.JobRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        JobManager.create(this).addJobCreator(DemoJobCreator())
    }
}

class DemoJobCreator : JobCreator {
    override fun create(tag: String): Job? {
        return when (tag) {
            DemoSyncJob.TAG -> DemoSyncJob()
            else -> null
        }
    }
}

class DemoSyncJob : Job() {
    override fun onRunJob(params: Params): Result {
        GlobalScope.launch (Dispatchers.IO){
            for (i in 0 until 10){
                Log.d(TAG, "onRunJob: $i")
                delay(1000)

            }
        }
        return Result.SUCCESS
    }

    companion object {
        const val TAG = "job_demo_tag"
        fun scheduleJob() {
            JobRequest.Builder(TAG)
                .setExecutionWindow(30000L, 40000L)
                .build()
                .schedule()
        }
    }
}