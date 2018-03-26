package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageWhiteBalanceFilter;

public class GPUImageWhiteBalanceAdjuster extends GPUImageAdjuster<GPUImageWhiteBalanceFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setTemperature(range(percentage, 2000.0f, 8000.0f));
        //getFilter().setTint(range(percentage, -100.0f, 100.0f));
    }
}