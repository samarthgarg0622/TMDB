package com.example.tmdb.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

class InternetConnectivityReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            Toast.makeText(context, "Back Online", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Oops! Network Lost", Toast.LENGTH_SHORT).show()
        }
    }


}