package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:21
 */
public class SepiaAdjuster extends FilterAdjuster<GPUImageSepiaFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setIntensity(range(percentage, 0.0f, 2.0f));
    }
}