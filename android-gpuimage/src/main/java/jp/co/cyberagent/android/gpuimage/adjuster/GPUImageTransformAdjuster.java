package jp.co.cyberagent.android.gpuimage.adjuster;

import android.opengl.Matrix;

import jp.co.cyberagent.android.gpuimage.GPUImageTransformFilter;

public class GPUImageTransformAdjuster extends GPUImageAdjuster<GPUImageTransformFilter> {
    @Override
    public void adjust(final int percentage) {
        float[] transform = new float[16];
        Matrix.setRotateM(transform, 0, 360 * percentage / 100, 0, 0, 1.0f);
        getFilter().setTransform3D(transform);
    }
}
