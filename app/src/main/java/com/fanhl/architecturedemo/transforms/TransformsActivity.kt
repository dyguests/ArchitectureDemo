package com.fanhl.architecturedemo.transforms

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.fanhl.architecturedemo.R
import com.fanhl.architecturedemo.extensions.observe
import kotlinx.android.synthetic.main.activity_transforms.*

class TransformsActivity : AppCompatActivity() {
    val l by lazy { MutableLiveData<Long>() }
    val s by lazy { Transformations.map(l) { it.toString() } }
    val i by lazy { Transformations.switchMap(l) { MutableLiveData<Int>().apply { value = (it % Int.MAX_VALUE).toInt() } } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transforms)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            l.value = System.currentTimeMillis()
        }

        l.observe(this) { Log.i(TAG, "onCreate l:" + it) }
        s.observe(this) { Log.i(TAG, "onCreate s:" + it) }
        i.observe(this) { Log.i(TAG, "onCreate i:" + it) }
    }

    companion object {
        private val TAG = TransformsActivity::class.java.simpleName
    }
}
