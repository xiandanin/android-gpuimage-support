package com.dyhdyh.gpuimage.support.example.filter;

import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup;
import jp.co.cyberagent.android.gpuimage.GPUImageGaussianBlurFilter;

/**
 * @author dengyuhan
 *         created 2018/3/19 16:49
 */
public class ApertureBlurFilter extends GPUImageFilterGroup {


    private GPUImageGaussianBlurFilter blurGFilter;
    private ApertureBlurBlendFilter blendFilter;

    private float amount;    //0~100
    private float[] center;    //center is (0.5, 0.5)
    private float radius;    //half width -> r = 1
    private float[] sizeRegionOfInterest;

    private int imageWidth = 1080;
    private int imageHeight = 1080;

    public ApertureBlurFilter() {
        super();
        blurGFilter = new GPUImageGaussianBlurFilter();
        addFilter(blurGFilter);

        blendFilter = new ApertureBlurBlendFilter();
        blendFilter.setTextSize(new float[]{1080, 1080});
        addFilter(blendFilter);
        //blurGFilter.addFilter(1, blendFilter);

        //getFilters().add(blurGFilter);

        setRadius(50f);
        //setAmount(50);
        setSizeRegionOfInterest(new float[]{1f, 1f});
        setCenter(new float[]{0.5f, 0.5f});
    }


    //0~100
    public void setRadius(float newValue) {
        radius = newValue / 100f;

        //0     0.1~0.32
        //50    0.32~0.85
        //100   0.5~1.5

        float innerr;
        float outerr;
        if (newValue < 0.5) {
            innerr = 0.1f + (0.32f - 0.1f) * radius / 0.5f;
            outerr = 0.32f + (0.85f - 0.32f) * radius / 0.5f;
        } else {
            innerr = 0.32f + (0.5f - 0.32f) * (radius - 0.5f) / 0.5f;
            outerr = 0.85f + (1.5f - 0.85f) * (radius - 0.5f) / 0.5f;
        }

        blendFilter.setInnerr(innerr / 2);
        blendFilter.setOuterr(outerr / 2);
    }


    public void setAmount(float newValue) {

        amount = newValue;
        //blendFilter.setAmount(newValue);

        float fr = 0.5f + 0.5f * (1.0f - amount / 100.0f);

        float width = imageWidth * fr;
        float height = imageHeight * fr;

        blurGFilter.setBlurSize(width);

    }

    public void setCenter(float[] newValue) {
        center = newValue;
        blendFilter.setCenter(newValue);
    }

    public void setSizeRegionOfInterest(float[] newValue) {
        sizeRegionOfInterest = newValue;
        blendFilter.setSizeRegionOfInterest(newValue);
    }


}