package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageHazeFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:25
 */
public class HazeAdjuster extends FilterAdjuster<GPUImageHazeFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setDistance(range(percentage, -0.3f, 0.3f));
        getFilter().setSlope(range(percentage, -0.3f, 0.3f));
    }
}