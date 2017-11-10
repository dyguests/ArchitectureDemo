package com.fanhl.architecturedemo.viewmodel

import android.arch.lifecycle.ViewModel
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.fanhl.architecturedemo.R

import kotlinx.android.synthetic.main.activity_chrono2.*
import kotlinx.android.synthetic.main.content_chrono2.*

class Chrono2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chrono2)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        chronometer.start()
    }
}

class ChronometerViewModel(
        var startDate: Long?
) : ViewModel()