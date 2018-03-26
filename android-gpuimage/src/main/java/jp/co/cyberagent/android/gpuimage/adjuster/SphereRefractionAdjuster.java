package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageSphereRefractionFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:25
 */
public class SphereRefractionAdjuster extends FilterAdjuster<GPUImageSphereRefractionFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setRadius(range(percentage, 0.0f, 1.0f));
    }
}