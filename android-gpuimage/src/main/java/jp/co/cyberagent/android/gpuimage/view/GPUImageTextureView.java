package jp.co.cyberagent.android.gpuimage.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

/**
 * @author dengyuhan
 *         created 2018/6/8 10:13
 */
public class GPUImageTextureView extends GLTextureView{
    private GPUImage mGPUImage;

    public GPUImageTextureView(Context context) {
        this(context,null);
    }

    public GPUImageTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGPUImage = new GPUImage(context);
        mGPUImage.setGLTextureView(this);
        mGPUImage.setScaleType(GPUImage.ScaleType.CENTER_INSIDE);
        mGPUImage.setBackgroundColor(Color.WHITE);
    }

    public void setImage(Bitmap bitmap) {
        mGPUImage.setImage(bitmap);
    }

    public void setFilter(GPUImageFilter filter) {
        mGPUImage.setFilter(filter);
    }

    public GPUImage getGPUImage() {
        return mGPUImage;
    }
}
