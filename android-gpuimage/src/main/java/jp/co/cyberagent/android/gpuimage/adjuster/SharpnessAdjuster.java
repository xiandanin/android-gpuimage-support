package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageSharpenFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:17
 */
public class SharpnessAdjuster extends FilterAdjuster<GPUImageSharpenFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setSharpness(range(percentage, -4.0f, 4.0f));
    }
}
