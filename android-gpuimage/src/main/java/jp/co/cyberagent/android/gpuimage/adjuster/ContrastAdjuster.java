package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:20
 */
public class ContrastAdjuster extends FilterAdjuster<GPUImageContrastFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setContrast(range(percentage, 0.0f, 2.0f));
    }
}