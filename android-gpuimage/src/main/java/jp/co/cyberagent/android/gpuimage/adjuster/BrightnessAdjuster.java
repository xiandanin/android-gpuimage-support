package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:21
 */
public class BrightnessAdjuster extends FilterAdjuster<GPUImageBrightnessFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setBrightness(range(percentage, -1.0f, 1.0f));
    }
}