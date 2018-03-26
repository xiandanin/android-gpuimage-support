package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageBilateralFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:26
 */
public class BilateralAdjuster extends FilterAdjuster<GPUImageBilateralFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setDistanceNormalizationFactor(range(percentage, 0.0f, 15.0f));
    }
}
