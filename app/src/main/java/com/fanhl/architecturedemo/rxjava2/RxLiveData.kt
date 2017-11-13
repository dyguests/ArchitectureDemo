package com.fanhl.architecturedemo.rxjava2

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import com.fanhl.architecturedemo.extensions.observe
import io.reactivex.Observable

/**
 * desc:
 * date: 2017/11/13
 *
 * @author fanhl
 */
class RxLiveData<T> : MutableLiveData<T>() {
    fun asObservable(owner: LifecycleOwner): Observable<T> {
        return Observable.create<T> { emitter ->
            observe(owner) { data ->
                try {
                    if (data != null) {
                        emitter.onNext(data)
                    } else {
                        emitter.onError(NullPointerException("com.fanhl.architecturedemo.rxjava2.RxLiveData.asObservable中发送的数据不能为null"))
                    }
                } catch (e: Exception) {
                    emitter.onError(e)
                }
            }
        }
    }
}