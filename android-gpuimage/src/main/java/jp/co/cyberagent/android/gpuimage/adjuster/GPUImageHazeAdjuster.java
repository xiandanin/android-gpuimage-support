package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageHazeFilter;

public class GPUImageHazeAdjuster extends GPUImageAdjuster<GPUImageHazeFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setDistance(range(percentage, -0.3f, 0.3f));
        getFilter().setSlope(range(percentage, -0.3f, 0.3f));
    }
}