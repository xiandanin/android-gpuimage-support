package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageDissolveBlendFilter;

public class GPUImageDissolveBlendAdjuster extends GPUImageAdjuster<GPUImageDissolveBlendFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setMix(range(percentage, 0.0f, 1.0f));
    }
}