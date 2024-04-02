package com.example.tmdb

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.tmdb.databinding.ActivityMainBinding
import com.example.tmdb.receiver.InternetConnectivityReceiver
import com.example.tmdb.utils.navigateSafe
import dagger.hilt.android.AndroidEntryPoint
import android.Manifest
import android.os.Build
import android.widget.Toast

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val internetConnectivityReceiver = InternetConnectivityReceiver()

    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navHostFragment.navController

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        registerReceiver(internetConnectivityReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.movie -> {
                    Toast.makeText(this, "Movie Clicked", Toast.LENGTH_SHORT).show()
                    return@setOnItemSelectedListener true
                }
                R.id.music -> {
                    Toast.makeText(this, "Music Clicked", Toast.LENGTH_SHORT).show()
                    return@setOnItemSelectedListener true
                }

                else -> {
                    Toast.makeText(this, "Music Clicked", Toast.LENGTH_SHORT).show()
                    return@setOnItemSelectedListener true
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.wish_list -> {
                navController.navigateSafe(R.id.wishListFragment)
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(internetConnectivityReceiver)
    }
}