package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageHueFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:20
 */
public class HueAdjuster extends FilterAdjuster<GPUImageHueFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setHue(range(percentage, 0.0f, 360.0f));
    }
}