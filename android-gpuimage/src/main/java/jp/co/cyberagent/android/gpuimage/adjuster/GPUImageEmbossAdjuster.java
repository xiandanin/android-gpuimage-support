package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageEmbossFilter;

public class GPUImageEmbossAdjuster extends GPUImageAdjuster<GPUImageEmbossFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setIntensity(range(percentage, 0.0f, 4.0f));
    }
}