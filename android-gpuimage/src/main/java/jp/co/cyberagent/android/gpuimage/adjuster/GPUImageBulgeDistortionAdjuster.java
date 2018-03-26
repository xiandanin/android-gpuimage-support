package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageBulgeDistortionFilter;

public class GPUImageBulgeDistortionAdjuster extends GPUImageAdjuster<GPUImageBulgeDistortionFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setRadius(range(percentage, 0.0f, 1.0f));
        getFilter().setScale(range(percentage, -1.0f, 1.0f));
    }
}