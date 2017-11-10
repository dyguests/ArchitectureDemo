/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fanhl.architecturedemo.viewmodel.step4

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.util.Log


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
