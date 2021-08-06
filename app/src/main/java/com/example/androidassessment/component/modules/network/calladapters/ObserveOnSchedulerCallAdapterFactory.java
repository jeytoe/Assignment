package com.example.androidassessment.component.modules.network.calladapters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class ObserveOnSchedulerCallAdapterFactory extends CallAdapter.Factory {

  public static ObserveOnSchedulerCallAdapterFactory create(Scheduler mainScheduler) {
    return new ObserveOnSchedulerCallAdapterFactory(mainScheduler);
  }

  private final Scheduler mainScheduler;

  private ObserveOnSchedulerCallAdapterFactory(Scheduler mainScheduler) {
    this.mainScheduler = mainScheduler;
  }

  @Override
  public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
    if (!getRawType(returnType).equals(Observable.class)) {
      return null;
    }

    final CallAdapter<Object, Observable<?>> delegate =
        (CallAdapter<Object, Observable<?>>) retrofit
            .nextCallAdapter(this, returnType, annotations);

    return new CallAdapter<Object, Object>() {
      @Override
      public Type responseType() {
        return delegate.responseType();
      }

      @Override
      public Object adapt(Call<Object> call) {
        return delegate.adapt(call)
            .observeOn(mainScheduler);
      }
    };
  }
}
