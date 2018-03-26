package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageRGBFilter;

public class GPUImageRGBAdjuster extends GPUImageAdjuster<GPUImageRGBFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setRed(range(percentage, 0.0f, 1.0f));
        //getFilter().setGreen(range(percentage, 0.0f, 1.0f));
        //getFilter().setBlue(range(percentage, 0.0f, 1.0f));
    }
}