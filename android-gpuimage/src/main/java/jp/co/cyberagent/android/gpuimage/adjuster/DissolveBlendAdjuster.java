package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageDissolveBlendFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:24
 */
public class DissolveBlendAdjuster extends FilterAdjuster<GPUImageDissolveBlendFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setMix(range(percentage, 0.0f, 1.0f));
    }
}