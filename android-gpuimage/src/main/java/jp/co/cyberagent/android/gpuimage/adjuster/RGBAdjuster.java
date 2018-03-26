package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageRGBFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:23
 */
public class RGBAdjuster extends FilterAdjuster<GPUImageRGBFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setRed(range(percentage, 0.0f, 1.0f));
        //getFilter().setGreen(range(percentage, 0.0f, 1.0f));
        //getFilter().setBlue(range(percentage, 0.0f, 1.0f));
    }
}