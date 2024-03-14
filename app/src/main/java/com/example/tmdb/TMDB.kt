package com.example.tmdb

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TMDB: Application() {
    override fun onCreate() {
        super.onCreate()
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             val channel =NotificationChannel(
                "foreground_service",
                "Running Service",
                NotificationManager.IMPORTANCE_HIGH)
             val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
             notificationManager.createNotificationChannel(channel)
        }
    }
}