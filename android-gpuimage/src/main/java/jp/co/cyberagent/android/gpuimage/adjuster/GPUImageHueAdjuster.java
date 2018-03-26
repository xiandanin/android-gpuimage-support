package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageHueFilter;

public class GPUImageHueAdjuster extends GPUImageAdjuster<GPUImageHueFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setHue(range(percentage, 0.0f, 360.0f));
    }
}