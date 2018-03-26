package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImage3x3TextureSamplingFilter;

public class GPUImage3x3TextureSamplingAdjuster extends GPUImageAdjuster<GPUImage3x3TextureSamplingFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setLineSize(range(percentage, 0.0f, 5.0f));
    }
}