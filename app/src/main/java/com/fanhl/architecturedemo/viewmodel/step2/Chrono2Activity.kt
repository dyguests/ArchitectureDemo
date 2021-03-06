package com.fanhl.architecturedemo.viewmodel.step2

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import com.fanhl.architecturedemo.R
import kotlinx.android.synthetic.main.activity_chrono.*

class Chrono2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chrono)
        setSupportActionBar(toolbar)

        val viewModel = ViewModelProviders.of(this).get(ChronometerViewModel::class.java)

        viewModel.startDate ?: run { viewModel.startDate = SystemClock.elapsedRealtime() }
        chronometer.base = viewModel.startDate!!

        chronometer.start()
    }
}

class ChronometerViewModel(
        var startDate: Long?
) : ViewModel() {
    constructor() : this(null)
}