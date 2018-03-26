package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageCrosshatchFilter;

public class GPUImageCrosshatchAdjuster extends GPUImageAdjuster<GPUImageCrosshatchFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setCrossHatchSpacing(range(percentage, 0.0f, 0.06f));
        getFilter().setLineWidth(range(percentage, 0.0f, 0.006f));
    }
}
