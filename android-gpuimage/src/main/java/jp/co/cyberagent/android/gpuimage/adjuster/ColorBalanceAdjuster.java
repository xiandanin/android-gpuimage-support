package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageColorBalanceFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:26
 */
public class ColorBalanceAdjuster extends FilterAdjuster<GPUImageColorBalanceFilter> {

    @Override
    public void adjust(int percentage) {
        getFilter().setMidtones(new float[]{
                range(percentage, 0.0f, 1.0f),
                range(percentage / 2, 0.0f, 1.0f),
                range(percentage / 3, 0.0f, 1.0f)});
    }
}