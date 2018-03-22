package com.dyhdyh.gpuimage.support.example.model;

import android.support.annotation.StringRes;

import java.util.List;

/**
 * @author dengyuhan
 *         created 2018/3/22 15:23
 */
public class FilterGroupModel {
    private @StringRes
    int groupNameRes;

    private List<FilterModel> filters;

    public FilterGroupModel() {
    }

    public FilterGroupModel(@StringRes int groupNameRes, List<FilterModel> filters) {
        this.groupNameRes = groupNameRes;
        this.filters = filters;
    }

    public int getGroupNameRes() {
        return groupNameRes;
    }

    public void setGroupNameRes(int groupNameRes) {
        this.groupNameRes = groupNameRes;
    }

    public List<FilterModel> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterModel> filters) {
        this.filters = filters;
    }
}
