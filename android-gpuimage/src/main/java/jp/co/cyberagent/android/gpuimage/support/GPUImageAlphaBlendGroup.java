package jp.co.cyberagent.android.gpuimage.support;

import android.graphics.Bitmap;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup;

/**
 * @author dengyuhan
 *         created 2018/6/25 11:47
 */
public class GPUImageAlphaBlendGroup extends GPUImageFilterGroup {
    private GPUImageAlphaBlendFilter2 mBlendFilter;

    public GPUImageAlphaBlendGroup() {
        this(0f);
    }

    public GPUImageAlphaBlendGroup(float mix) {
        mBlendFilter = new GPUImageAlphaBlendFilter2(mix);
    }

    public void setSecondFilter(GPUImageFilter filter) {
        this.mBlendFilter.setSecondFilter(filter);

        getFilters().clear();
        getFilters().add(new GPUImageFilter());
        getFilters().add(mBlendFilter);
        updateMergedFilters();
    }

    public void setBitmap(final Bitmap bitmap) {
        this.mBlendFilter.setBitmap(bitmap);
    }

    public void setMix(float mix) {
        this.mBlendFilter.setMix(mix);
    }
}
