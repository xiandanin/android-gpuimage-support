package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageSharpenFilter;

public class GPUImageSharpenAdjuster extends GPUImageAdjuster<GPUImageSharpenFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setSharpness(range(percentage, -4.0f, 4.0f));
    }
}
