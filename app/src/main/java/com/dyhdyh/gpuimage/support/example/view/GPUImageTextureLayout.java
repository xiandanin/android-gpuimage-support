package com.dyhdyh.gpuimage.support.example.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dyhdyh.gpuimage.support.video.view.GPUImageVideoView;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.view.GPUImageTextureView;

/**
 * @author dengyuhan
 *         created 2018/6/6 16:19
 */
public class GPUImageTextureLayout extends FrameLayout {
    private GPUImageTextureView mImageView;
    private GPUImageVideoView mVideoView;

    public GPUImageTextureLayout(@NonNull Context context) {
        this(context, null);
    }

    public GPUImageTextureLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GPUImageTextureLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setDataSource(String path) {
        setDataSource(!path.endsWith(".mp4"), path);
    }

    public void setDataSource(boolean image, String path) {
        final LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.CENTER;
        if (image) {
            mImageView = new GPUImageTextureView(getContext());
            mImageView.setScaleType(GPUImage.ScaleType.VIEW_FIT_CENTER);
            mImageView.setLayoutParams(lp);
            mImageView.setImageBitmap(BitmapFactory.decodeFile(path));
            addView(mImageView);
        } else {
            mVideoView = new GPUImageVideoView(getContext());
            mVideoView.setLayoutParams(lp);
            mVideoView.setDataSource(path);
            addView(mVideoView);
        }
    }

    public void setFilter(GPUImageFilter filter) {
        if (mImageView != null) {
            mImageView.setFilter(filter);
        } else {
            mVideoView.setFilter(filter);
        }
    }

    public void start() {
        if (mVideoView != null) {
            mVideoView.prepare();
            mVideoView.start();
        }
    }

    public void requestRender() {
        if (mImageView != null) {
            mImageView.requestRender();
        } else {
            //mVideoView.requestRender();
        }
    }

    public GPUImage getGPUImage() {
        return mImageView.getGPUImage();
    }
}
