package mraqs.water.notification.worker

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import mraqs.water.App
import mraqs.water.manager.CallManager
import mraqs.water.manager.NetManager
import mraqs.water.notification.Overlay

class OverlayWorker(private val context: Context, params: WorkerParameters) : Worker(context, params) {
    companion object {
        const val OVERLAY_JOB_ID = 45678
        private const val TAG = "Work"
    }

    override fun doWork(): Result {
        if (App.appInBackground.value!! && !NetManager(context).isConnectedToInternet && !CallManager(context).nowCalling)
            showOverlay()
        Log.d(TAG, "doWork: Overlay Work Complete")
        return Result.success()
    }

    private fun showOverlay() {
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val job = JobInfo.Builder(OVERLAY_JOB_ID, ComponentName(context, Overlay::class.java))
            .setOverrideDeadline(0)
            .setRequiresDeviceIdle(true)
            .build()
        jobScheduler.schedule(job)
    }
}