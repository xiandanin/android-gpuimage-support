package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageEmbossFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:21
 */
public class EmbossAdjuster extends FilterAdjuster<GPUImageEmbossFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setIntensity(range(percentage, 0.0f, 4.0f));
    }
}