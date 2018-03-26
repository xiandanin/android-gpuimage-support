package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageGammaFilter;

public class GPUImageGammaAdjuster extends GPUImageAdjuster<GPUImageGammaFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setGamma(range(percentage, 0.0f, 3.0f));
    }
}