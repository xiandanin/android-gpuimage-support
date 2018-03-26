package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageVignetteFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:24
 */
public class VignetteAdjuster extends FilterAdjuster<GPUImageVignetteFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setVignetteStart(range(percentage, 0.0f, 1.0f));
    }
}