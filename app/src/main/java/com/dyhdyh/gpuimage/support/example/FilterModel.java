package com.dyhdyh.gpuimage.support.example;

import java.util.ArrayList;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorInvertFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageExposureFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGammaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHighlightShadowFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLevelsFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageMonochromeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageOpacityFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageRGBFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageToneCurveFilter;

/**
 * @author dengyuhan
 *         created 2018/3/21 19:18
 */
public class FilterModel {
    private String filterName;
    private GPUImageFilter filter;
    private boolean checked;

    public FilterModel(String filterName, GPUImageFilter filter) {
        this.filterName = filterName;
        this.filter = filter;
    }

    public FilterModel() {
    }

    public static List<FilterModel> generateAll() {
        List<FilterModel> models = new ArrayList<>();
        //调整颜色 Handle Color
        models.add(new FilterModel("亮度", new GPUImageBrightnessFilter()));
        models.add(new FilterModel("曝光", new GPUImageExposureFilter()));
        models.add(new FilterModel("对比度", new GPUImageContrastFilter()));
        models.add(new FilterModel("饱和度", new GPUImageSaturationFilter()));
        models.add(new FilterModel("伽马线", new GPUImageGammaFilter()));
        models.add(new FilterModel("反色", new GPUImageColorInvertFilter()));
        models.add(new FilterModel("褐色（怀旧）", new GPUImageSepiaFilter()));
        models.add(new FilterModel("色阶", new GPUImageLevelsFilter()));
        models.add(new FilterModel("灰度", new GPUImageGrayscaleFilter()));
        models.add(new FilterModel("RGB", new GPUImageRGBFilter()));
        models.add(new FilterModel("色调曲线", new GPUImageToneCurveFilter()));
        models.add(new FilterModel("单色", new GPUImageMonochromeFilter()));
        models.add(new FilterModel("不透明度", new GPUImageOpacityFilter()));
        models.add(new FilterModel("提亮阴影", new GPUImageHighlightShadowFilter()));
        return models;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
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
