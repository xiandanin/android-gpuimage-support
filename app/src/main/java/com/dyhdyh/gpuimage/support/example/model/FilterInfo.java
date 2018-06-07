package com.dyhdyh.gpuimage.support.example.model;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

/**
 * @author dengyuhan
 *         created 2018/6/7 20:07
 */
public class FilterInfo {
    private GPUImageFilter filter;
    private float min;
    private float max;
    private float progress;
    private String name;
    private float defaultValue;

    public FilterInfo(GPUImageFilter filter, String name, float min, float max, float defaultValue) {
        this.filter = filter;
        this.name = name;
        this.min = min;
        this.max = max;
        this.defaultValue = defaultValue;
    }

    public GPUImageFilter getFilter() {
        return filter;
    }

    public void setFilter(GPUImageFilter filter) {
        this.filter = filter;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(float defaultValue) {
        this.defaultValue = defaultValue;
    }
}
