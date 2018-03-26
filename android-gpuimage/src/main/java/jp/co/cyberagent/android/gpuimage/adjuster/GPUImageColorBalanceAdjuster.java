package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageColorBalanceFilter;

public class GPUImageColorBalanceAdjuster extends GPUImageAdjuster<GPUImageColorBalanceFilter> {

    @Override
    public void adjust(int percentage) {
        getFilter().setMidtones(new float[]{
                range(percentage, 0.0f, 1.0f),
                range(percentage / 2, 0.0f, 1.0f),
                range(percentage / 3, 0.0f, 1.0f)});
    }
}