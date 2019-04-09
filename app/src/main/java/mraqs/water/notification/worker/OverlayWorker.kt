package mraqs.water.notification.worker

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import mraqs.water.notification.Overlay
import mraqs.water.App
import mraqs.water.manager.NetManager

class OverlayWorker(private val context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        if (App.appInBackground.value!! && NetManager(context).isConnectedToInternet)
            showOverlay()
        return Result.success()
    }

    private fun showOverlay() {
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val job = JobInfo.Builder(45678, ComponentName(context, Overlay::class.java))
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .build()
        jobScheduler.schedule(job)
    }
}