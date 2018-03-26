package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageGlassSphereFilter;

public class GPUImageGlassSphereAdjuster extends GPUImageAdjuster<GPUImageGlassSphereFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setRadius(range(percentage, 0.0f, 1.0f));
    }
}