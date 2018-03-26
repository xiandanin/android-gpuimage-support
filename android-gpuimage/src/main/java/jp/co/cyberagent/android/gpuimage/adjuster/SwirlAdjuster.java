package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageSwirlFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:26
 */
public class SwirlAdjuster extends FilterAdjuster<GPUImageSwirlFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setAngle(range(percentage, 0.0f, 2.0f));
    }
}
