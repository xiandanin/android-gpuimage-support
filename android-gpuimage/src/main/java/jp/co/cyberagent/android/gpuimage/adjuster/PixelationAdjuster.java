package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImagePixelationFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:19
 */
public class PixelationAdjuster extends FilterAdjuster<GPUImagePixelationFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setPixel(range(percentage, 1.0f, 100.0f));
    }
}