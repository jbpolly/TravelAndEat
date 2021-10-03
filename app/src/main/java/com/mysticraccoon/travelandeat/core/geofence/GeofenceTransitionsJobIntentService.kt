package com.mysticraccoon.travelandeat.core.geofence

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class GeofenceTransitionsJobIntentService : JobIntentService(), CoroutineScope {

    private var coroutineJob: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + coroutineJob

    companion object {
        private const val JOB_ID = 573

        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(
                context,
                GeofenceTransitionsJobIntentService::class.java, JOB_ID,
                intent
            )
        }
    }

    override fun onHandleWork(intent: Intent) {
//        if (intent.action == SaveReminderFragment.ACTION_GEOFENCE_EVENT) {
//            val geofencingEvent = GeofencingEvent.fromIntent(intent)
//            if (geofencingEvent.hasError()) {
//                return
//            }
//            if (geofencingEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
//                Log.v(GEOFENCE_TAG, "Entered geofence")
//                sendNotification(geofencingEvent.triggeringGeofences)
//            }
//        }
    }

    private fun sendNotification(triggeringGeofences: List<Geofence>) {
//        val remindersDao = LocalDB.createRemindersDao(this)
//        if(triggeringGeofences.isEmpty()){
//            Log.e(GEOFENCE_TAG, "No Geofence Trigger Found! Abort mission!")
//            return
//        }else{
//            for(trigger in triggeringGeofences){
//                val requestId = trigger.requestId
//                CoroutineScope(coroutineContext).launch(SupervisorJob()) {
//                    val result = remindersDao.getReminderById(requestId)
//                    result?.let { item->
//                        sendNotification(this@GeofenceTransitionsJobIntentService, item.toReminderDataItem())
//                    }
//                }
//            }
//        }
    }

}