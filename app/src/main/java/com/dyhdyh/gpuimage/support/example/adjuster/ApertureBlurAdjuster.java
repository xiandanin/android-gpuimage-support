package com.dyhdyh.gpuimage.support.example.adjuster;

import com.dyhdyh.gpuimage.support.example.filter.GPUImageApertureBlurFilter;

import jp.co.cyberagent.android.gpuimage.adjuster.GPUImageAdjuster;

/**
 * 光圈模糊调节
 * @author dengyuhan
 *         created 2018/3/26 17:23
 */
public class ApertureBlurAdjuster extends GPUImageAdjuster<GPUImageApertureBlurFilter> {
    @Override
    public void adjust(int percentage) {
        getFilter().setBlurSize(range(percentage, 0.0f, 1.0f));
    }
}
