package jp.co.cyberagent.android.gpuimage.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

/**
 * @author dengyuhan
 *         created 2018/6/8 10:13
 */
public class GPUImageTextureView extends GLTextureView {
    private GPUImage mGPUImage;

    private Bitmap mBitmap;
    private GPUImage.ScaleType mScaleType;

    public GPUImageTextureView(Context context) {
        this(context, null);
    }

    public GPUImageTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGPUImage = new GPUImage(context);
        mGPUImage.setGLTextureView(this);
        setScaleType(GPUImage.ScaleType.CENTER_CROP);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (GPUImage.ScaleType.VIEW_FIT_CENTER == mScaleType && mBitmap != null) {
            final int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
            final int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

            final int dwidth = mBitmap.getWidth();
            final int dheight = mBitmap.getHeight();

            final int vwidth = widthSpecSize - getPaddingLeft() - getPaddingRight();
            final int vheight = heightSpecSize - getPaddingTop() - getPaddingBottom();

            float scale = Math.min((float) vwidth / (float) dwidth, (float) vheight / (float) dheight);

            float dx = vwidth - dwidth * scale;
            float dy = vheight - dheight * scale;

            int outputWidth = (int) Math.ceil(vwidth - dx);
            int outputHeight = (int) Math.ceil(vheight - dy);

            if (outputWidth != 0 && outputHeight != 0) {
                setMeasuredDimension(outputWidth, outputHeight);
            } else {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }

    public void setImageBitmap(Bitmap bitmap) {
        mGPUImage.setImage(bitmap);
        this.mBitmap = bitmap;
    }

    @Override
    public void setBackgroundColor(int color) {
        mGPUImage.setBackgroundColor(color);
    }

    public void setFilter(GPUImageFilter filter) {
        mGPUImage.setFilter(filter);
    }

    public void setScaleType(GPUImage.ScaleType scaleType) {
        this.mScaleType = scaleType;
        mGPUImage.setScaleType(GPUImage.ScaleType.VIEW_FIT_CENTER == scaleType ? GPUImage.ScaleType.CENTER_INSIDE : scaleType);
    }

    public GPUImage getGPUImage() {
        return mGPUImage;
    }
}
