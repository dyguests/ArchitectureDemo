package com.fanhl.architecturedemo.viewmodel.step5

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.fanhl.architecturedemo.R
import com.fanhl.architecturedemo.extensions.observe
import kotlinx.android.synthetic.main.fragment_step5.*

class Step5Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step5)
    }
}

class Step5Fragment : Fragment() {
    private lateinit var viewModel: SeekBarViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_step5, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 注意两行代码的区别
//        viewModel = ViewModelProviders.of(this).get(SeekBarViewModel::class.java)
        viewModel = ViewModelProviders.of(activity).get(SeekBarViewModel::class.java)

        subscribeSeekBar()
    }

    private fun subscribeSeekBar() {
        seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                viewModel.seekbarValue.value = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        viewModel.seekbarValue.observe(this) {
            seek_bar.progress = it!!
        }
    }
}

class SeekBarViewModel : ViewModel() {
    val seekbarValue = MutableLiveData<Int>()
}