package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageLevelsFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:26
 */
public class LevelsMinMidAdjuster extends FilterAdjuster<GPUImageLevelsFilter> {
    @Override
    public void adjust(int percentage) {
        getFilter().setMin(0.0f, range(percentage, 0.0f, 1.0f), 1.0f);
    }
}