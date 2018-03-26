package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageGaussianBlurFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:24
 */
public class GaussianBlurAdjuster extends FilterAdjuster<GPUImageGaussianBlurFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setBlurSize(range(percentage, 0.0f, 1.0f));
    }
}