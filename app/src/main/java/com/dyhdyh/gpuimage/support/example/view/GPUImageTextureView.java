package com.dyhdyh.gpuimage.support.example.view;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dyhdyh.gpuimage.support.video.render.RenderHandler;
import com.dyhdyh.gpuimage.support.video.render.RenderThread;

import java.io.IOException;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

/**
 * @author dengyuhan
 *         created 2018/3/19 14:21
 */
public class GPUImageTextureView extends FrameLayout implements TextureView.SurfaceTextureListener {
    private static final String TAG = "GPUImageTextureView";

    private TextureView mTextureView;
    private MediaPlayer mMediaPlayer;
    private RenderThread mRenderThread;

    public GPUImageTextureView(Context context) {
        super(context);
        initView(context);
    }

    public GPUImageTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public GPUImageTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setLooping(true);
        }

        mRenderThread = new RenderThread();
        mRenderThread.setName("TexFromCam Render");
        mRenderThread.start();
        mRenderThread.waitUntilReady();

        mTextureView = new TextureView(context);
        mTextureView.setSurfaceTextureListener(this);
        mRenderThread.setMediaPlayer(mMediaPlayer);

        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        addView(mTextureView, 0, params);
    }

    public void setDataSource(String path) {
        try {
            mMediaPlayer.setDataSource(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDataSource(int resId) {
        setDataSource(Uri.parse("android.resource://" + getContext().getPackageName() + "/" + resId));
    }

    public void setDataSource(Uri uri) {
        try {
            mMediaPlayer.setDataSource(getContext(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Log.d(TAG, "onSurfaceTextureAvailable surfaceTexture=" + surface.hashCode() + "," + Thread.currentThread().getName());

        if (mRenderThread != null) {
            // Normal case -- render thread is running, tell it about the new surface.
            RenderHandler rh = mRenderThread.getHandler();
            Log.d(TAG, "onSurfaceTextureAvailable RenderHandler=" + rh);
            rh.sendSurfaceAvailable(surface, width, height);
        } else {
            // Sometimes see this on 4.4.x N5: power off, power on, unlock, with device in
            // landscape and a lock screen that requires portrait.  The surface-created
            // message is showing up after onPause().
            //
            // Chances are good that the surface will be destroyed before the activity is
            // unpaused, but we track it anyway.  If the activity is un-paused and we start
            // the RenderThread, the SurfaceHolder will be passed in right after the thread
            // is created.
            Log.i(TAG, "render thread not running");
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        Log.d(TAG, "onSurfaceTextureSizeChanged surfaceTexture=" + surface.hashCode() + "," + Thread.currentThread().getName());
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        Log.d(TAG, "onSurfaceTextureDestroyed surfaceTexture=" + surface.hashCode() + "," + Thread.currentThread().getName());
        if (mRenderThread != null) {
            RenderHandler rh = mRenderThread.getHandler();
            rh.sendSurfaceDestroyed(surface);
        }
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        //Log.d(TAG, "onSurfaceTextureUpdated surfaceTexture=" + surface.hashCode()+ "," + Thread.currentThread().getName());

    }

    public void pause() {
        mMediaPlayer.pause();
    }

    public void prepare() {
        try {
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        mMediaPlayer.start();
    }

    public void stop() {
        mMediaPlayer.stop();
    }

    public void release() {
        mMediaPlayer.release();
    }

    public void setFilter(GPUImageFilter filter) {
        if (mRenderThread != null) {
            RenderHandler rh = mRenderThread.getHandler();
            rh.sendSetFilter(filter);
        }
    }

}
