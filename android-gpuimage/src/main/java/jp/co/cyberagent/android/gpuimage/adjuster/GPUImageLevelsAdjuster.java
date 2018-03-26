package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageLevelsFilter;

public class GPUImageLevelsAdjuster extends GPUImageAdjuster<GPUImageLevelsFilter> {
    @Override
    public void adjust(int percentage) {
        getFilter().setMin(0.0f, range(percentage, 0.0f, 1.0f), 1.0f);
    }
}