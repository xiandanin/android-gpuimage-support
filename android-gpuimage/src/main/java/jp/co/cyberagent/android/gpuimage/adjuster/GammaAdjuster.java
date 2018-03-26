package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageGammaFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:21
 */
public class GammaAdjuster extends FilterAdjuster<GPUImageGammaFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setGamma(range(percentage, 0.0f, 3.0f));
    }
}