package com.fanhl.architecturedemo.rxjava2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.fanhl.architecturedemo.R
import kotlinx.android.synthetic.main.activity_rxjava2.*

class Rxjava2Activity : AppCompatActivity() {
    val liveData by lazy { RxLiveData<Long>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava2)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { liveData.value = System.currentTimeMillis() }

        liveData
                .asObservable(this)
                .subscribe { Log.i(TAG, "case1 data:$it") }
        liveData
                .asObservable(this)
        liveData
                .asObservable(this)
                .subscribe { Log.i(TAG, "case2 data:$it") }
    }

    companion object {
        private val TAG = Rxjava2Activity::class.java.simpleName
    }
}
