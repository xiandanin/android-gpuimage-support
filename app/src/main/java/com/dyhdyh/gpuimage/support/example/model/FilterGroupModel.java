package com.dyhdyh.gpuimage.support.example.model;

/**
 * @author dengyuhan
 *         created 2018/6/7 20:04
 */
public class FilterGroupModel {
    private boolean checked;

    private String groupName;
    private FilterInfo saturation;//饱和
    private FilterInfo noise;//噪点
    private FilterInfo temperature;//色温
    private FilterInfo hightlight;//高光
    private FilterInfo exposure;//曝光度
    private FilterInfo shadow;//阴影
    private FilterInfo brightness;//亮度
    private FilterInfo contrast;//对比度

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public FilterInfo getSaturation() {
        return saturation;
    }

    public void setSaturation(FilterInfo saturation) {
        this.saturation = saturation;
    }

    public FilterInfo getNoise() {
        return noise;
    }

    public void setNoise(FilterInfo noise) {
        this.noise = noise;
    }

    public FilterInfo getTemperature() {
        return temperature;
    }

    public void setTemperature(FilterInfo temperature) {
        this.temperature = temperature;
    }

    public FilterInfo getHightlight() {
        return hightlight;
    }

    public void setHightlight(FilterInfo hightlight) {
        this.hightlight = hightlight;
    }

    public FilterInfo getExposure() {
        return exposure;
    }

    public void setExposure(FilterInfo exposure) {
        this.exposure = exposure;
    }

    public FilterInfo getShadow() {
        return shadow;
    }

    public void setShadow(FilterInfo shadow) {
        this.shadow = shadow;
    }

    public FilterInfo getBrightness() {
        return brightness;
    }

    public void setBrightness(FilterInfo brightness) {
        this.brightness = brightness;
    }

    public FilterInfo getContrast() {
        return contrast;
    }

    public void setContrast(FilterInfo contrast) {
        this.contrast = contrast;
    }
}
