package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageSwirlFilter;

public class GPUImageSwirlAdjuster extends GPUImageAdjuster<GPUImageSwirlFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setAngle(range(percentage, 0.0f, 2.0f));
    }
}
