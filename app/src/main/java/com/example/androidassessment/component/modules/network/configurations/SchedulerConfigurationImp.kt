package com.example.androidassessment.component.modules.network.configurations

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerConfigurationImp: SchedulerConfiguration {
    override fun ioScheduler(): Scheduler {
        return Schedulers.io()
    }

    override fun mainScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
