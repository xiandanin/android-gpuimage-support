package com.dyhdyh.gpuimage.support.video.render;

import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.os.Looper;
import android.util.Log;
import android.view.Surface;

import com.dyhdyh.gpuimage.support.video.filter.GPUImageExtTexFilter;
import com.dyhdyh.gpuimage.support.video.gles.EglCore;
import com.dyhdyh.gpuimage.support.video.gles.GlUtil;
import com.dyhdyh.gpuimage.support.video.gles.WindowSurface;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup;
import jp.co.cyberagent.android.gpuimage.util.TextureRotationUtil;


/**
 * Thread that handles all rendering and camera operations.
 */
public class RenderThread extends Thread implements
        SurfaceTexture.OnFrameAvailableListener {
    private static final String TAG = "RenderThread";
    // Object must be created on render thread to get correct Looper, but is used from
    // UI thread, so we need to declare it volatile to ensure the UI thread sees a fully
    // constructed object.
    private volatile RenderHandler mHandler;

    // Used to wait for the thread to start.
    private Object mStartLock = new Object();
    private boolean mReady = false;

    private EglCore mEglCore;

    private int mWindowSurfaceWidth;
    private int mWindowSurfaceHeight;

    private Surface mSurface;

    int mTextureId = -1;

    final float CUBE[] = {
            -1.0f, -1.0f,
            1.0f, -1.0f,
            -1.0f, 1.0f,
            1.0f, 1.0f,
    };
    private FloatBuffer mGLCubeBuffer;
    private FloatBuffer mGLTextureBuffer;

    private WindowSurface mWindowSurface;
    private GPUImageFilterGroup mFilter;
    private GPUImageFilter mCurrentFilter;

    // Receives the output from the camera preview.
    private SurfaceTexture mCameraTexture;
    private MediaPlayer mMediaPlayer;


    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mMediaPlayer = mediaPlayer;
    }

    /**
     * Thread entry point.
     */
    @Override
    public void run() {
        Log.d(TAG, "run-->" + Thread.currentThread().getName());
        Looper.prepare();

        // We need to create the Handler before reporting ready.
        mHandler = new RenderHandler(this);
        synchronized (mStartLock) {
            mReady = true;
            mStartLock.notify();    // signal waitUntilReady()
        }

        // Prepare EGL and open the camera before we start handling messages.
        mEglCore = new EglCore(null, 0);
        //mMediaPlayer.start();

        Looper.loop();

        Log.i(TAG, "looper quit");
        //mMediaPlayer.release();
//            releaseGl(null);

        mFilter.destroy();

        mEglCore.release();

        synchronized (mStartLock) {
            mReady = false;
        }
    }

    /**
     * Waits until the render thread is ready to receive messages.
     * <p>
     * Call from the UI thread.
     */
    public void waitUntilReady() {
        synchronized (mStartLock) {
            while (!mReady) {
                try {
                    mStartLock.wait();
                } catch (InterruptedException ie) { /* not expected */ }
            }
        }
    }

    /**
     * Shuts everything down.
     */
    public void shutdown() {
        Log.i(TAG, "shutdown");
        Looper.myLooper().quit();
    }

    /**
     * Returns the render thread's Handler.  This may be called from any thread.
     */
    public RenderHandler getHandler() {
        return mHandler;
    }

    /**
     * Handles the surface-created callback from SurfaceView.  Prepares GLES and the Surface.
     */
    public void surfaceAvailable(SurfaceTexture holder, int width, int height) {
        Log.i(TAG, "RenderThread surfaceCreated holder=" + holder.hashCode() + ",(" + width + "," + height + ")," + Thread.currentThread().getName());
//            if (holders.contains(holder)) {
//                // added before
//                Log.e(TAG, "surfaceAvailable holder contains should never comein");
//            }
//            holders.add(holder);

//            if (!windowSurfacesMap.containsKey(holder)) {
        mSurface = new Surface(holder);
        mWindowSurface = new WindowSurface(mEglCore, mSurface, false);
        synchronized (mWindowSurface) {
            mWindowSurface.makeCurrent();
        }
//            }

        mWindowSurfaceWidth = width;
        mWindowSurfaceHeight = height;

        // only create once

        mTextureId = getPreviewTexture();
        Log.i(TAG, "mTextureId=" + mTextureId);
        mCameraTexture = new SurfaceTexture(mTextureId);

        mGLCubeBuffer = ByteBuffer.allocateDirect(CUBE.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        mGLCubeBuffer.put(CUBE).position(0);

        mGLTextureBuffer = ByteBuffer.allocateDirect(TextureRotationUtil.TEXTURE_NO_ROTATION.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        mGLTextureBuffer.put(TextureRotationUtil.TEXTURE_NO_ROTATION).position(0);

//            if (!newSurface) {
//                // This Surface was established on a previous run, so no surfaceChanged()
//                // message is forthcoming.  Finish the surface setup now.
//                //
//                // We could also just call this unconditionally, and perhaps do an unnecessary
//                // bit of reallocating if a surface-changed message arrives.
//                mWindowSurfaceWidth = mWindowSurface.getWidth();
//                mWindowSurfaceHeight = mWindowSurface.getHeight();
//                finishSurfaceSetup(holder);
//            }
        Log.i(TAG, "surfaceChanged should only once here");
        // create all filter

        mFilter = new GPUImageFilterGroup();
        mFilter.addFilter(new GPUImageExtTexFilter());
        if (mCurrentFilter != null) {
            mFilter.addFilter(mCurrentFilter);
        }

        mFilter.init();
        mFilter.onOutputSizeChanged(width, height);

        mCameraTexture.setOnFrameAvailableListener(this);
        finishSurfaceSetup();
    }

    public void setFilter(GPUImageFilter filter) {
        //Log.i(TAG, "setFilter--->" + filter + "," + Thread.currentThread().getName());

        final GPUImageFilter oldFilter = mFilter;
        this.mCurrentFilter = filter;
        if (oldFilter != null) {
            oldFilter.destroy();
        }
        mFilter = new GPUImageFilterGroup();
        mFilter.addFilter(new GPUImageExtTexFilter());
        if (mCurrentFilter != null) {
            mFilter.addFilter(mCurrentFilter);
        }

        mFilter.init();
        GLES20.glUseProgram(mFilter.getProgram());
        mFilter.onOutputSizeChanged(mWindowSurfaceWidth, mWindowSurfaceHeight);

    }

    public int getPreviewTexture() {
        int textureId = -1;
        if (textureId == GlUtil.NO_TEXTURE) {
            textureId = GlUtil.createTextureObject(GLES11Ext.GL_TEXTURE_EXTERNAL_OES);
        }
        return textureId;
    }

    /**
     * Releases most of the GL resources we currently hold (anything allocated by
     * surfaceAvailable()).
     * <p>
     * Does not release EglCore.
     */
    private void releaseGl(SurfaceTexture surfaceHolder) {
        GlUtil.checkGlError("releaseGl start");

//            synchronized (windowSurfacesMap) {
        if (mWindowSurface != null) {
            mWindowSurface.release();
//                holders.remove(surfaceHolder);
//                    holderMap.remove(surfaceHolder);
//                windowSurface.
        }
//            }

        GlUtil.checkGlError("releaseGl done");

//            mEglCore.makeNothingCurrent();
    }

    /**
     * Handles the surfaceChanged message.
     * <p>
     * We always receive surfaceChanged() after surfaceCreated(), but surfaceAvailable()
     * could also be called with a Surface created on a previous run.  So this may not
     * be called.
     */
    public void surfaceChanged(SurfaceTexture surfaceHolder, int width, int height) {
        Log.i(TAG, "RenderThread surfaceChanged " + width + "x" + height + ";surfaceHolder=" + surfaceHolder.hashCode() + "," + Thread.currentThread().getName());


    }

    /**
     * Handles the surfaceDestroyed message.
     */
    public void surfaceDestroyed(SurfaceTexture surfaceHolder) {
        // In practice this never appears to be called -- the activity is always paused
        // before the surface is destroyed.  In theory it could be called though.
//            Log.i(TAG, "RenderThread surfaceDestroyed holder=" + surfaceHolder.hashCode());
        releaseGl(surfaceHolder);
//            Log.i(TAG, "RenderThread surfaceDestroyed done;holder=" + surfaceHolder.hashCode());
    }

    /**
     * Sets up anything that depends on the window size.
     * <p>
     * Open the camera (to set mCameraAspectRatio) before calling here.
     */
    private void finishSurfaceSetup() {
        mMediaPlayer.setSurface(new Surface(mCameraTexture));
    }

    @Override   // SurfaceTexture.OnFrameAvailableListener; runs on arbitrary thread
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        mHandler.sendFrameAvailable();
    }

    /**
     * Handles incoming frame of data from the camera.
     */
    public void frameAvailable() {
        mCameraTexture.updateTexImage();
        draw();
    }

    /**
     * Draws the scene and submits the buffer.
     */
    public void draw() {
//            synchronized (windowSurfacesMap) {
        if (mFilter != null) {
            //Log.i(TAG, "RenderThread draw ," + Thread.currentThread().getName());

            GlUtil.checkGlError("draw start >");
            mWindowSurface.makeCurrent();
            mFilter.onDraw(mTextureId, mGLCubeBuffer, mGLTextureBuffer);
            mWindowSurface.swapBuffers();
            GlUtil.checkGlError("draw done >");
        }
//            }


//            Log.i(TAG, "frameAvailable draw texture end");
    }

}