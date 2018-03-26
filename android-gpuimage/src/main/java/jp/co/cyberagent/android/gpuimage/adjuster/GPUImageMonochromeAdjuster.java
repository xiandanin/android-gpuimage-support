package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageMonochromeFilter;

public class GPUImageMonochromeAdjuster extends GPUImageAdjuster<GPUImageMonochromeFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setIntensity(range(percentage, 0.0f, 1.0f));
        //getFilter().setColor(new float[]{0.6f, 0.45f, 0.3f, 1.0f});
    }
}