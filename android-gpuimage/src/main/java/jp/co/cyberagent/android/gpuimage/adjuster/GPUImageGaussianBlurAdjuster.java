package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageGaussianBlurFilter;

public class GPUImageGaussianBlurAdjuster extends GPUImageAdjuster<GPUImageGaussianBlurFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setBlurSize(range(percentage, 0.0f, 1.0f));
    }
}