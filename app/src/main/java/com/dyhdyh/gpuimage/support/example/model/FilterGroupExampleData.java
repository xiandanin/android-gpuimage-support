package com.dyhdyh.gpuimage.support.example.model;

import java.util.ArrayList;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;

/**
 * @author dengyuhan
 *         created 2018/6/7 20:10
 */
public class FilterGroupExampleData {

    public static List<FilterGroupModel> all() {
        List<FilterGroupModel> list = new ArrayList<>();
        final FilterGroupModel groupModel = new FilterGroupModel();
        groupModel.setGroupName("滤镜组");
        groupModel.setBrightness(new FilterInfo(new GPUImageBrightnessFilter(),"亮度", -1f, 1f, 0.4f));
        groupModel.setSaturation(new FilterInfo(new GPUImageSaturationFilter(),"饱和", -1f, 1f, 0.4f));
        list.add(groupModel);
        return list;
    }


    public static List<FilterInfo> baseFilters() {
        List<FilterInfo> list = new ArrayList<>();
        list.add(new FilterInfo(new GPUImageBrightnessFilter(),"亮度", -100, 100, 0));
        list.add(new FilterInfo(new GPUImageSaturationFilter(),"饱和", -100, 100, 0));
        return list;
    }
}
