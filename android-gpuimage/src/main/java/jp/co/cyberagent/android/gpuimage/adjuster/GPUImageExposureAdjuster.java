package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageExposureFilter;

public class GPUImageExposureAdjuster extends GPUImageAdjuster<GPUImageExposureFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setExposure(range(percentage, -10.0f, 10.0f));
    }
}