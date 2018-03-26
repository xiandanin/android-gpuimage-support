package jp.co.cyberagent.android.gpuimage.adjuster;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

/**
 * 滤镜调节器
 *
 * @author dengyuhan
 *         created 2018/3/26 10:13
 */
public abstract class GPUImageAdjuster<Filter extends GPUImageFilter> {
    private Filter mFilter;

    public GPUImageAdjuster<Filter> filter(final Filter filter) {
        this.mFilter = filter;
        return this;
    }

    public Filter getFilter() {
        return mFilter;
    }

    public abstract void adjust(int percentage);

    protected float range(final int percentage, final float start, final float end) {
        return (end - start) * percentage / 100.0f + start;
    }

    protected int range(final int percentage, final int start, final int end) {
        return (end - start) * percentage / 100 + start;
    }
}
