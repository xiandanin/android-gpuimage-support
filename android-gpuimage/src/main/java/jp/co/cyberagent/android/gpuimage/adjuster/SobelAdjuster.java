package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageSobelEdgeDetection;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:21
 */
public class SobelAdjuster extends FilterAdjuster<GPUImageSobelEdgeDetection> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setLineSize(range(percentage, 0.0f, 5.0f));
    }
}