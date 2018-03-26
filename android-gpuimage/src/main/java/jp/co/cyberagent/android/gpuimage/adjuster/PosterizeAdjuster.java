package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImagePosterizeFilter;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:22
 */
public class PosterizeAdjuster extends FilterAdjuster<GPUImagePosterizeFilter> {
    @Override
    public void adjust(final int percentage) {
        // In theorie to 256, but only first 50 are interesting
        getFilter().setColorLevels(range(percentage, 1, 50));
    }
}