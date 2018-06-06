package com.dyhdyh.gpuimage.support.example.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.view.GLTextureView;

/**
 * @author dengyuhan
 *         created 2018/6/6 16:19
 */
public class GPUImageTextureLayout extends FrameLayout {
    private GPUImage mGPUImage;
    private GLTextureView mTextureView;

    public GPUImageTextureLayout(@NonNull Context context) {
        this(context, null);
    }

    public GPUImageTextureLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GPUImageTextureLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mGPUImage = new GPUImage(context);

        mTextureView = new GLTextureView(context);
        mTextureView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mGPUImage.setGLTextureView(mTextureView);
        addView(mTextureView);

    }

    public void setImage(Bitmap file) {
        mGPUImage.setImage(file);
    }

    public void setFilter(GPUImageFilter filter) {
        mGPUImage.setFilter(filter);
    }

    public void requestRender() {
        mGPUImage.requestRender();
    }
}
