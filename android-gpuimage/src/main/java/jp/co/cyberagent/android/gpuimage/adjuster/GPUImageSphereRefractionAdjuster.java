package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageSphereRefractionFilter;

public class GPUImageSphereRefractionAdjuster extends GPUImageAdjuster<GPUImageSphereRefractionFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setRadius(range(percentage, 0.0f, 1.0f));
    }
}