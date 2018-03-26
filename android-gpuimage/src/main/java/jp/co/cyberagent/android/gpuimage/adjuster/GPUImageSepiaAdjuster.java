package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;

public class GPUImageSepiaAdjuster extends GPUImageAdjuster<GPUImageSepiaFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setIntensity(range(percentage, 0.0f, 2.0f));
    }
}