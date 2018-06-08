package com.dyhdyh.gpuimage.support.example.filter;

import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;

/**
 * -100 - 100
 * @author dengyuhan
 *         created 2018/6/8 14:28
 */
public class DearBrightnessFilter extends GPUImageBrightnessFilter {

    public DearBrightnessFilter() {
        this(0);
    }

    public DearBrightnessFilter(int brightness) {
        super((float) brightness / 100);
    }

    public void setBrightness(int brightness) {
        super.setBrightness((float) brightness / 100);
    }
}
