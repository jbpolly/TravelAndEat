package com.mysticraccoon.travelandeat.core.geofence

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.mysticraccoon.travelandeat.core.utils.triggerNotification
import com.mysticraccoon.travelandeat.data.repository.SavePlaceRepository
import com.mysticraccoon.travelandeat.ui.addEditMeal.AddEditMealFragment.Companion.ACTION_GEOFENCE_EVENT
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import kotlin.coroutines.CoroutineContext

class GeofenceTransitionsJobIntentService : JobIntentService(), CoroutineScope {

    private var coroutineJob: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + coroutineJob

    private val savedPlaceRepository: SavePlaceRepository by inject()
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
        if (intent.action == ACTION_GEOFENCE_EVENT) {
            val geofencingEvent = GeofencingEvent.fromIntent(intent)
            if (geofencingEvent.hasError()) {
                return
            }
            if (geofencingEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
                Log.v(GEOFENCE_TAG, "Entered geofence")
                sendNotification(geofencingEvent.triggeringGeofences)
            }
        }
    }

    private fun sendNotification(triggeringGeofences: List<Geofence>) {
        if(triggeringGeofences.isEmpty()){
            Log.e(GEOFENCE_TAG, "No Geofence Trigger Found! Abort mission!")
            return
        }else{
            for(trigger in triggeringGeofences){
                val requestId = trigger.requestId
                CoroutineScope(coroutineContext).launch(SupervisorJob()) {
                    val result = savedPlaceRepository.getSavedPlaceById(requestId)
                    result?.let { item->
                        triggerNotification(this@GeofenceTransitionsJobIntentService, item)
                    }
                }
            }
        }
    }
}