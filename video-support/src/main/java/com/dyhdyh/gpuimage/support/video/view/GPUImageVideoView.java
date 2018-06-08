package com.dyhdyh.gpuimage.support.video.view;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;

import com.dyhdyh.gpuimage.support.video.render.RenderHandler;
import com.dyhdyh.gpuimage.support.video.render.RenderThread;

import java.io.IOException;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

/**
 * @author dengyuhan
 *         created 2018/6/8 10:14
 */
public class GPUImageVideoView extends TextureView implements TextureView.SurfaceTextureListener {

    private final String TAG = "GPUImageVideoView";

    private MediaPlayer mMediaPlayer;
    private RenderThread mRenderThread;

    public GPUImageVideoView(Context context) {
        this(context, null);
    }

    public GPUImageVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    private void initView() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setLooping(true);
        }

        mRenderThread = new RenderThread();
        mRenderThread.setName("RenderThread");
        mRenderThread.start();
        mRenderThread.waitUntilReady();

        this.setSurfaceTextureListener(this);
        mRenderThread.setMediaPlayer(mMediaPlayer);
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

    public void setFilter(GPUImageFilter filter) {
        if (mRenderThread != null) {
            RenderHandler rh = mRenderThread.getHandler();
            rh.sendSetFilter(filter);
        }
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

    public Object getGPUImage() {
        return mRenderThread;
    }
}
