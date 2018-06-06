package com.dyhdyh.gpuimage.support.rxjava2;

/**
 * @author dengyuhan
 *         created 2018/6/6 18:25
 */
public interface FunctionDelegate<Return> {

    Return run() throws Exception;
}
