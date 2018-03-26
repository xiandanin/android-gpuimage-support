package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;

public class GPUImageBrightnessAdjuster extends GPUImageAdjuster<GPUImageBrightnessFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setBrightness(range(percentage, -1.0f, 1.0f));
    }
}