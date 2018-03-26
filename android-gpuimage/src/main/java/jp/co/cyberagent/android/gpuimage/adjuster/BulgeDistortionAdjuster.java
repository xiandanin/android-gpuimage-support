package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageBulgeDistortionFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:25
 */
public class BulgeDistortionAdjuster extends FilterAdjuster<GPUImageBulgeDistortionFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setRadius(range(percentage, 0.0f, 1.0f));
        getFilter().setScale(range(percentage, -1.0f, 1.0f));
    }
}