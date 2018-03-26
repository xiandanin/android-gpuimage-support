package jp.co.cyberagent.android.gpuimage.adjuster;

import android.opengl.Matrix;

import jp.co.cyberagent.android.gpuimage.GPUImageTransformFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:27
 */
public class RotateAdjuster extends FilterAdjuster<GPUImageTransformFilter> {
    @Override
    public void adjust(final int percentage) {
        float[] transform = new float[16];
        Matrix.setRotateM(transform, 0, 360 * percentage / 100, 0, 0, 1.0f);
        getFilter().setTransform3D(transform);
    }
}
