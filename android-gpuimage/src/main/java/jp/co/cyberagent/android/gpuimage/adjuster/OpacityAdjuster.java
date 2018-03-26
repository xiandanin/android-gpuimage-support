package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageOpacityFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:23
 */
public class OpacityAdjuster extends FilterAdjuster<GPUImageOpacityFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setOpacity(range(percentage, 0.0f, 1.0f));
    }
}