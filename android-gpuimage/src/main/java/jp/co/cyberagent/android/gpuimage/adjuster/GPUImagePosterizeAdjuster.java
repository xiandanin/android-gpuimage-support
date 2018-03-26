package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImagePosterizeFilter;

public class GPUImagePosterizeAdjuster extends GPUImageAdjuster<GPUImagePosterizeFilter> {
    @Override
    public void adjust(final int percentage) {
        // In theorie to 256, but only first 50 are interesting
        getFilter().setColorLevels(range(percentage, 1, 50));
    }
}