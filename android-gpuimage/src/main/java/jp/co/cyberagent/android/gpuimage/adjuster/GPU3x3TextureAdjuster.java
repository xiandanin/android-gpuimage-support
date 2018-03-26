package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImage3x3TextureSamplingFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:22
 */
public class GPU3x3TextureAdjuster extends FilterAdjuster<GPUImage3x3TextureSamplingFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setLineSize(range(percentage, 0.0f, 5.0f));
    }
}