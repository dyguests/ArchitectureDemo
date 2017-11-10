package com.fanhl.architecturedemo.viewmodel.step4

import android.Manifest
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.fanhl.architecturedemo.R
import kotlinx.android.synthetic.main.activity_location.*

class LocationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_LOCATION_PERMISSION_CODE)
        } else {
            bindLocationListener()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            bindLocationListener()
        } else {
            Toast.makeText(this, "This sample requires Location access", Toast.LENGTH_LONG).show()
        }
    }

    private fun bindLocationListener() {
        BoundLocationManager.bindLocationListenerIn(this, object : LocationListener {
            override fun onLocationChanged(location: Location) {
                tv_location.text = location.latitude.toString() + ", " + location.longitude
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

            override fun onProviderEnabled(provider: String) {
                Toast.makeText(this@LocationActivity, "Provider enabled: " + provider, Toast.LENGTH_SHORT).show()
            }

            override fun onProviderDisabled(provider: String) {}
        }, applicationContext)
    }

    companion object {
        private val REQUEST_LOCATION_PERMISSION_CODE = 1
    }

}

object BoundLocationManager {
    fun bindLocationListenerIn(lifecycleOwner: AppCompatActivity, listener: LocationListener, context: Context) {
        BoundLocationListener(lifecycleOwner, listener, context)
    }

    @SuppressWarnings("MissingPermission")
    internal class BoundLocationListener(
            lifecycleOwner: AppCompatActivity,
            private val listener: LocationListener,
            private val context: Context
    ) : LifecycleObserver {
        private var locationManager: LocationManager? = null

        init {
            lifecycleOwner.lifecycle.addObserver(this)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun addLocationListener() {
            // Note: Use the Fused Location Provider from Google Play Services instead.
            // https://developers.google.com/android/reference/com/google/android/gms/location/FusedLocationProviderApi

            locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            assert(locationManager != null)
            locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, listener)
            Log.d("BoundLocationMgr", "Listener added")

            // Force an update with the last location, if available.
            val lastLocation = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (lastLocation != null) {
                listener.onLocationChanged(lastLocation)
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun removeLocationListener() {
            if (locationManager == null) {
                return
            }
            locationManager!!.removeUpdates(listener)
            locationManager = null
            Log.d("BoundLocationMgr", "Listener removed")
        }
    }
}