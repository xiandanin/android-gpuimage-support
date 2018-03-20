package com.dyhdyh.gpuimage.support.example;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;

import jp.co.cyberagent.android.gpuimage.GPUImageRenderer;

/**
 * @author dengyuhan
 *         created 2018/3/19 14:21
 */
public class GPUImageTextureView extends TextureView implements TextureView.SurfaceTextureListener {
    private static final String TAG = "GPUImageTextureView";
    private MediaPlayer mPlayer;
    private GPUImageRenderer mRenderer;

    public GPUImageTextureView(Context context) {
        super(context);
    }

    public GPUImageTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GPUImageTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context){
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
