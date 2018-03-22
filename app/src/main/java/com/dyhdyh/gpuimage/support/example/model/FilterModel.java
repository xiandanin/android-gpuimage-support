package com.dyhdyh.gpuimage.support.example.model;

import android.support.annotation.StringRes;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

/**
 * @author dengyuhan
 *         created 2018/3/21 19:18
 */
public class FilterModel {
    private boolean isFilterGroupLabel;
    private @StringRes int filterGroupNameRes;
    private @StringRes int filterNameRes;
    private GPUImageFilter filter;
    private boolean checked;

    public FilterModel(@StringRes int filterNameRes, GPUImageFilter filter) {
        this.filterNameRes = filterNameRes;
        this.filter = filter;
    }

    public FilterModel(int filterGroupNameRes) {
        this.isFilterGroupLabel = true;
        this.filterGroupNameRes = filterGroupNameRes;
    }

    public FilterModel() {
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
}
