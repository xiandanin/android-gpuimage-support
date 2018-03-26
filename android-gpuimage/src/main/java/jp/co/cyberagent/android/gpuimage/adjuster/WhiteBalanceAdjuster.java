package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageWhiteBalanceFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:23
 */
public class WhiteBalanceAdjuster extends FilterAdjuster<GPUImageWhiteBalanceFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setTemperature(range(percentage, 2000.0f, 8000.0f));
        //getFilter().setTint(range(percentage, -100.0f, 100.0f));
    }
}