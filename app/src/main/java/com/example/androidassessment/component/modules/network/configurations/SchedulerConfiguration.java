package com.example.androidassessment.component.modules.network.configurations;

import io.reactivex.Scheduler;

public interface SchedulerConfiguration {

  Scheduler ioScheduler();

  Scheduler mainScheduler();
}
