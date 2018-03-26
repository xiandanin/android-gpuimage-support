package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImagePixelationFilter;

public class GPUImagePixelationAdjuster extends GPUImageAdjuster<GPUImagePixelationFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setPixel(range(percentage, 1.0f, 100.0f));
    }
}