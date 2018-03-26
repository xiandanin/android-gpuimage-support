package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageHighlightShadowFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:23
 */
public class HighlightShadowAdjuster extends FilterAdjuster<GPUImageHighlightShadowFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setShadows(range(percentage, 0.0f, 1.0f));
        getFilter().setHighlights(range(percentage, 0.0f, 1.0f));
    }
}