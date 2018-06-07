package com.dyhdyh.gpuimage.support.rxjava2;


import rx.Observable;
import rx.Subscriber;

/**
 * rxjava1的泛型适配器
 * @author dengyuhan
 *         created 2018/6/6 18:22
 */
public class GPUImageRxJava1Adapter<T> implements GPUImageRxJavaAdapter<Observable<T>, T> {

    public static <T> GPUImageRxJava1Adapter<T> create() {
        return new GPUImageRxJava1Adapter<>();
    }

    @Override
    public Observable<T> asObservable(final FunctionDelegate<T> delegate) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(delegate.run());
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        });
    }
}
