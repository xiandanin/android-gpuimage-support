package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;

public class GPUImageContrastAdjuster extends GPUImageAdjuster<GPUImageContrastFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setContrast(range(percentage, 0.0f, 2.0f));
    }
}