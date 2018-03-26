package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageOpacityFilter;

public class GPUImageOpacityAdjuster extends GPUImageAdjuster<GPUImageOpacityFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setOpacity(range(percentage, 0.0f, 1.0f));
    }
}