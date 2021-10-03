package com.mysticraccoon.travelandeat.core.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.mysticraccoon.travelandeat.BuildConfig
import com.mysticraccoon.travelandeat.R
import com.mysticraccoon.travelandeat.data.SavedPlace
import com.mysticraccoon.travelandeat.ui.main.MainActivity

private const val NOTIFICATION_CHANNEL_ID = BuildConfig.APPLICATION_ID + ".channel"
const val SAVED_PLACE_KEY = "savedPlace"

fun triggerNotification(context: Context, savedPlace: SavedPlace) {

    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
        && notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) == null
    ) {
        val name = context.getString(R.string.app_name)
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            name,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    val intent = NavDeepLinkBuilder(context)
        .setComponentName(MainActivity::class.java)
        .setGraph(R.navigation.nav_graph)
        .setDestination(R.id.savedPlaceDetailsFragment)
        .setArguments(bundleOf(SAVED_PLACE_KEY to savedPlace))
        .createPendingIntent()


//    build the notification object with the data to be shown
    val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle(context.getString(R.string.geofence_notification_title, savedPlace.location))
        .setContentText(context.getString(R.string.geofence_description, savedPlace.dishName))
        .setContentIntent(intent)
        .setAutoCancel(true)
        .build()

    notificationManager.notify(getUniqueId(), notification)
}

private fun getUniqueId() = ((System.currentTimeMillis() % 10000).toInt())