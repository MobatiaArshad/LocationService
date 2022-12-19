package com.example.locationservice

import android.Manifest
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import com.example.locationservice.adapter.MyLocationsAdapter
import com.example.locationservice.databinding.ActivityMainBinding
import com.example.locationservice.locationListener.CurrentLocation
import com.example.locationservice.locationListener.GetLocation
import com.example.locationservice.room.AppDatabase
import com.example.locationservice.room.Entity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    lateinit var currentLocation: CurrentLocation

    lateinit var appDatabase: AppDatabase
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        appDatabase = AppDatabase.getDatabase(applicationContext)

        currentLocation = CurrentLocation(this, object : GetLocation {
            override fun onLocationChanged(location: Location) {

                val isAirplaneModeOn = isAirplaneModeOn(this@MainActivity)
                val time = SimpleDateFormat("h:mm a", Locale.ENGLISH).format(Calendar.getInstance().time)

                appDatabase.userDao().insertLocation(
                    Entity(
                        lat = location.latitude,
                        lng = location.longitude,
                        time = time,
                        airplaneMode = isAirplaneModeOn
                    )
                )

                binding.recycler.adapter = MyLocationsAdapter(appDatabase.userDao().getAllLoc() as ArrayList<Entity>)
            }

            override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {
                println("DATA: STATUS_CHANGE => $s")
            }

            override fun onProviderEnabled(s: String) {
                // IS GPS TURNED ON
                println("DATA: PROVIDER_ENABLED => $s")
            }

            override fun onProviderDisabled(s: String) {
                // IS GPS TURNED OFF
                println("DATA: PROVIDER_DISABLED => $s")
            }

        })

        binding.deleteIco.setOnClickListener {
            appDatabase.userDao().deleteAllData()
            binding.recycler.adapter = MyLocationsAdapter(appDatabase.userDao().getAllLoc() as ArrayList<Entity>)
        }

        binding.reloadIco.setOnClickListener {
            currentLocation.getLoc()
            binding.recycler.adapter?.notifyDataSetChanged()
        }
    }

    /**
     * Gets the state of Airplane Mode.
     *
     * @param context
     * @return true if enabled.
     */
    fun isAirplaneModeOn(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Settings.System.getInt(
                context.contentResolver,
                Settings.System.AIRPLANE_MODE_ON, 0
            ) != 0
        } else {
            Settings.Global.getInt(
                context.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON, 0
            ) != 0
        }
    }
}