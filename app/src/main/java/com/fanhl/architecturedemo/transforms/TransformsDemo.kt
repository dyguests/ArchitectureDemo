package com.fanhl.architecturedemo.transforms

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations

/**
 * desc:
 * date: 2017/11/13
 *
 * @author fanhl
 */
class TransformsDemo {
    internal fun a() {
        val l = MutableLiveData<Long>()
        val s = Transformations.map(l) { it.toString() }


        l.value = 1L
        l.value = 2L
    }
}
