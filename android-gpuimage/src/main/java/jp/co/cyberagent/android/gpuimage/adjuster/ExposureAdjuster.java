package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageExposureFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:22
 */
public class ExposureAdjuster extends FilterAdjuster<GPUImageExposureFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setExposure(range(percentage, -10.0f, 10.0f));
    }
}