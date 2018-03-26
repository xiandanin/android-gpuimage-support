package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageSobelEdgeDetection;

public class GPUImageSobelEdgeDetectionAdjuster extends GPUImageAdjuster<GPUImageSobelEdgeDetection> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setLineSize(range(percentage, 0.0f, 5.0f));
    }
}