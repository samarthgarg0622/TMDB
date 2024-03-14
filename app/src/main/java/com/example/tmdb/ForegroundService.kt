package com.example.tmdb

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat

class ForegroundService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTIONS.START.toString() -> start()
            ACTIONS.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, "foreground_service")
            .setSmallIcon(R.drawable.placeholder)
            .setContentTitle("My Service Test")
            .setContentText("I am running a service!!")
            .build()
        startForeground(1,notification)
    }
    enum class ACTIONS {
        START, STOP
    }
}