package com.example.androidassessment.component.modules.network.calladapters;

import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.Result;

public class HttpExceptionCallAdapterFactory extends CallAdapter.Factory {

  public static HttpExceptionCallAdapterFactory create() {
    return new HttpExceptionCallAdapterFactory();
  }

  public HttpExceptionCallAdapterFactory() {
  }

  @Override
  public CallAdapter get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
    CallAdapter nextCallAdapter = retrofit.nextCallAdapter(this, returnType, annotations);

    if (getRawType(returnType).equals(Observable.class)) {
      return new ObservableAdapter(nextCallAdapter);
    }

    return null;
  }

  private static class ObservableAdapter implements CallAdapter<Object, Observable<Object>> {

    private final CallAdapter<Object, Observable<Object>> nextCallAdapter;

    public ObservableAdapter(CallAdapter<Object, Observable<Object>> nextCallAdapter) {
      this.nextCallAdapter = nextCallAdapter;
    }

    @Override
    public Type responseType() {
      return nextCallAdapter.responseType();
    }

    @Override
    public Observable<Object> adapt(Call<Object> call) {
      return nextCallAdapter.adapt(call)
          .flatMap(object -> {
            Response response = parseResponse(object);
            if (response != null && response.code() >= 400) {
              return Observable.error(new HttpException(response));
            }

            return Observable.just(object);
          });
    }

    @Nullable
    private Response parseResponse(Object object) {
      if (object instanceof Response) {
        return (Response) object;
      } else if (object instanceof Result) {
        return ((Result) object).response();
      }
      return null;
    }
  }
}
