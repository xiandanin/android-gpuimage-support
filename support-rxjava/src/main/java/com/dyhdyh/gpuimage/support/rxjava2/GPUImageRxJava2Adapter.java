package com.dyhdyh.gpuimage.support.rxjava2;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * rxjava2的泛型适配器
 * @author dengyuhan
 *         created 2018/6/6 18:22
 */
public class GPUImageRxJava2Adapter<T> implements GPUImageRxJavaAdapter<Observable<T>, T> {

    public static <T> GPUImageRxJava2Adapter<T> create() {
        return new GPUImageRxJava2Adapter<>();
    }

    @Override
    public Observable<T> asObservable(final FunctionDelegate<T> delegate) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(delegate.run());
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
                emitter.onComplete();
            }
        });
    }
}
