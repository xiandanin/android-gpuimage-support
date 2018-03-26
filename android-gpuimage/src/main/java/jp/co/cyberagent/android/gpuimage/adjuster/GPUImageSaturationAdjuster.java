package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;

public class GPUImageSaturationAdjuster extends GPUImageAdjuster<GPUImageSaturationFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setSaturation(range(percentage, 0.0f, 2.0f));
    }
}
