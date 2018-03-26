package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageBilateralFilter;

public class GPUImageBilateralAdjuster extends GPUImageAdjuster<GPUImageBilateralFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setDistanceNormalizationFactor(range(percentage, 0.0f, 15.0f));
    }
}
