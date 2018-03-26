package com.dyhdyh.gpuimage.support.example.model;

import android.graphics.Bitmap;
import android.support.annotation.StringRes;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.adjuster.GPUImageAdjuster;

/**
 * @author dengyuhan
 *         created 2018/3/21 19:18
 */
public class FilterModel {
    private boolean isFilterGroupLabel;
    private @StringRes
    int filterGroupNameRes;

    private @StringRes
    int filterNameRes;

    private GPUImageFilter filter;
    private GPUImageAdjuster adjuster;

    private Bitmap coverBitmap;
    private int progress;
    private boolean checked;


    public FilterModel(@StringRes int filterNameRes, GPUImageFilter filter) {
        this(filterNameRes, filter, null, 0);
    }

    public FilterModel(@StringRes int filterNameRes, GPUImageFilter filter, GPUImageAdjuster adjuster, int defaultProgress) {
        this.filterNameRes = filterNameRes;
        this.filter = filter;
        this.adjuster = adjuster;
        if (this.adjuster != null) {
            this.adjuster.filter(this.filter);
            this.progress = defaultProgress;
        }
    }

    public FilterModel(int filterGroupNameRes) {
        this.isFilterGroupLabel = true;
        this.filterGroupNameRes = filterGroupNameRes;
    }

    public FilterModel() {
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public boolean isFilterGroupLabel() {
        return isFilterGroupLabel;
    }

    public void setFilterGroupLabel(boolean filterGroupLabel) {
        isFilterGroupLabel = filterGroupLabel;
    }

    public int getFilterGroupNameRes() {
        return filterGroupNameRes;
    }

    public void setFilterGroupNameRes(int filterGroupNameRes) {
        this.filterGroupNameRes = filterGroupNameRes;
    }

    public int getFilterNameRes() {
        return filterNameRes;
    }

    public void setFilterNameRes(int filterNameRes) {
        this.filterNameRes = filterNameRes;
    }

    public GPUImageFilter getFilter() {
        return filter;
    }

    public void setFilter(GPUImageFilter filter) {
        this.filter = filter;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Bitmap getCoverBitmap() {
        return coverBitmap;
    }

    public void setCoverBitmap(Bitmap coverBitmap) {
        this.coverBitmap = coverBitmap;
    }

    public GPUImageAdjuster getAdjuster() {
        return adjuster;
    }

    public void setAdjuster(GPUImageAdjuster adjuster) {
        this.adjuster = adjuster;
    }
}
