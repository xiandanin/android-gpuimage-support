package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageVignetteFilter;

public class GPUImageVignetteAdjuster extends GPUImageAdjuster<GPUImageVignetteFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setVignetteStart(range(percentage, 0.0f, 1.0f));
    }
}