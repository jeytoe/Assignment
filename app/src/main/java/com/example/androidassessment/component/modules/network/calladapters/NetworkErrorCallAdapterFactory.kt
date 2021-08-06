package com.example.androidassessment.component.modules.network.calladapters

import android.util.Log
import com.example.androidassessment.BuildConfig
import com.google.gson.Gson
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkErrorCallAdapterFactory @Inject constructor(private val gson: Gson) :
    CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val nextCallAdapter = retrofit.nextCallAdapter(this, returnType, annotations)
        return if (getRawType(returnType) == Observable::class.java) {
            ObservableAdapter(
                nextCallAdapter as CallAdapter<Any?, Observable<Any?>>, gson
            )
        } else null
    }

    private class ObservableAdapter(
        private val nextAdapter: CallAdapter<Any?, Observable<Any?>>,
        private val gson: Gson
    ) : CallAdapter<Any?, Observable<Any?>> {
        override fun responseType(): Type {
            return nextAdapter.responseType()
        }

        override fun adapt(call: Call<Any?>): Observable<Any?> {
            return nextAdapter.adapt(call)
                .onErrorResumeNext { throwable: Throwable ->
                    setLogs("")
                    printTheStackTrace(throwable)
                    Observable.error(throwable)
                }
        }

        private fun setLogs(logs: String) {
            if (BuildConfig.DEBUG) {
                Log.d("Test Log ", "NetworkErrorCallAdapterFactory $logs")
            }
        }

        private fun printTheStackTrace(e: Throwable) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        fun create(gson: Gson): NetworkErrorCallAdapterFactory {
            return NetworkErrorCallAdapterFactory(gson)
        }
    }
}
