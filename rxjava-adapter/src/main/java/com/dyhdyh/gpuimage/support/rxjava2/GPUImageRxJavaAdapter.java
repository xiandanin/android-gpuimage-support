package com.dyhdyh.gpuimage.support.rxjava2;

/**
 * @author dengyuhan
 *         created 2018/6/6 18:29
 */
public interface GPUImageRxJavaAdapter<Observable, Return> {

    Observable asObservable(FunctionDelegate<Return> delegate);
}
