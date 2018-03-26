package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageGlassSphereFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:25
 */
public class GlassSphereAdjuster extends FilterAdjuster<GPUImageGlassSphereFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setRadius(range(percentage, 0.0f, 1.0f));
    }
}