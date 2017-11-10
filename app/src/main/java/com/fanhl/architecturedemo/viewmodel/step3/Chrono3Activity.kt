package com.fanhl.architecturedemo.viewmodel.step3

import android.arch.lifecycle.*
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.fanhl.architecturedemo.R
import kotlinx.android.synthetic.main.chrono_activity_3.*
import java.util.*

class Chrono3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chrono_activity_3)

        val mLiveDataTimerViewModel = ViewModelProviders.of(this).get(LiveDataTimerViewModel::class.java)

        mLiveDataTimerViewModel.elapsedTime.observe(this) {
            timer_textview.text = resources.getString(R.string.seconds, it)
        }
    }
}

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T?) -> Unit) {
    observe(owner, Observer { it -> observer(it) })
}

class LiveDataTimerViewModel : ViewModel() {
    val elapsedTime = MutableLiveData<Long>()

    private val mInitialTime: Long = SystemClock.elapsedRealtime()

    init {
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val newValue = (SystemClock.elapsedRealtime() - mInitialTime) / 1000

                Handler(Looper.getMainLooper()).post {
                    Log.i(TAG, "run ")
                    elapsedTime.value = newValue
                }
            }
        }, ONE_SECOND.toLong(), ONE_SECOND.toLong())
    }

    companion object {
        private val TAG = LiveDataTimerViewModel::class.java.simpleName
        private const val ONE_SECOND = 1000

    }
}