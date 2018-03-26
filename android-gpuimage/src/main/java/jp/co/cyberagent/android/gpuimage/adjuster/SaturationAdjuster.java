package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:22
 */
public class SaturationAdjuster extends FilterAdjuster<GPUImageSaturationFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setSaturation(range(percentage, 0.0f, 2.0f));
    }
}
