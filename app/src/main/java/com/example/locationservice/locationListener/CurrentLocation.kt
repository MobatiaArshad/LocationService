package com.example.locationservice.locationListener

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat


class CurrentLocation(private val context: Context,private val getLocation: GetLocation) : LocationListener {

    var locationManager: LocationManager? = null
    var provider: String? = null

    fun getLoc() {
        // Getting LocationManager object
        locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager?

        // Creating an empty criteria object
        val criteria = Criteria()

        // Getting the name of the provider that meets the criteria
        provider = locationManager!!.getBestProvider(criteria, false)
        if (provider != null && provider != " ") {

            // Get the location from the given provider
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                println("Provider found")
            }

            val location = locationManager!!.getLastKnownLocation(provider!!)

            locationManager!!.requestLocationUpdates(provider!!, 1000, 1f, this)

            if (location != null) onLocationChanged(location) else {
                Toast.makeText(context, "Location can't be retrieved", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "No Provider Found", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onLocationChanged(location: Location) {

        getLocation!!.onLocationChanged(location)
        locationManager?.removeUpdates(this)
    }

    override fun onStatusChanged(s: String, i: Int, bundle: Bundle?) {
        getLocation!!.onStatusChanged(s, i, bundle!!)
    }

    override fun onProviderEnabled(s: String) {
        getLocation!!.onProviderEnabled(s)
    }

    override fun onProviderDisabled(s: String) {
        getLocation!!.onProviderDisabled(s)
        // Toast.makeText(context, "Please turn on the GPS to get current location.", Toast.LENGTH_SHORT).show();
        try {
            Toast.makeText(context, "Please turn on the GPS to get current location.", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e("exception", "$e==")
        }
    }


}