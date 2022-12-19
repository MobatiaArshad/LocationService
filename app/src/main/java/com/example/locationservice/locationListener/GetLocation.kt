package com.example.locationservice.locationListener

import android.location.Location
import android.os.Bundle

interface GetLocation {
    fun onLocationChanged(location: Location)
    fun onStatusChanged(s: String, i: Int,bundle: Bundle)
    fun onProviderEnabled(s: String)
    fun onProviderDisabled(s: String)
}