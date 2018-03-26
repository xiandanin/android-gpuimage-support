package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageHighlightShadowFilter;

public class GPUImageHighlightShadowAdjuster extends GPUImageAdjuster<GPUImageHighlightShadowFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setShadows(range(percentage, 0.0f, 1.0f));
        getFilter().setHighlights(range(percentage, 0.0f, 1.0f));
    }
}