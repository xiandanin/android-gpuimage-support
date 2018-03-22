package com.dyhdyh.gpuimage.support.video.render;

/**
 * @author dengyuhan
 * created 2018/3/21 15:13
 */

import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

/**
 * Handler for RenderThread.  Used for messages sent from the UI thread to the render thread.
 * <p>
 * The object is created on the render thread, and the various "send" methods are called
 * from the UI thread.
 */
public class RenderHandler extends Handler {
    private static final int MSG_SURFACE_AVAILABLE = 0;
    private static final int MSG_SURFACE_CHANGED = 1;
    private static final int MSG_SURFACE_DESTROYED = 2;
    private static final int MSG_SHUTDOWN = 3;
    private static final int MSG_FRAME_AVAILABLE = 4;
    private static final int MSG_SET_FILTER = 5;
    private static final int MSG_REDRAW = 9;

    private static final String TAG = "RenderHandler";

    // This shouldn't need to be a weak ref, since we'll go away when the Looper quits,
    // but no real harm in it.
    private WeakReference<RenderThread> mWeakRenderThread;

    /**
     * Call from render thread.
     */
    public RenderHandler(RenderThread rt) {
//            super();
        mWeakRenderThread = new WeakReference<RenderThread>(rt);
    }

    /**
     * Sends the "surface available" message.  If the surface was newly created (i.e.
     * this is called from surfaceCreated()), set newSurface to true.  If this is
     * being called during Activity startup for a previously-existing surface, set
     * newSurface to false.
     * <p>
     * The flag tells the caller whether or not it can expect a surfaceChanged() to
     * arrive very soon.
     * <p>
     * Call from UI thread.
     */
    public void sendSurfaceAvailable(SurfaceTexture holder, int width, int height) {
        sendMessage(obtainMessage(MSG_SURFACE_AVAILABLE,
                width, height, holder));
    }

    /**
     * Sends the "surface changed" message, forwarding what we got from the SurfaceHolder.
     * <p>
     * Call from UI thread.
     */
    public void sendSurfaceChanged(SurfaceTexture holder, int width,
                                   int height) {
        // ignore format
        sendMessage(obtainMessage(MSG_SURFACE_CHANGED, width, height, holder));
    }

    /**
     * Sends the "shutdown" message, which tells the render thread to halt.
     * <p>
     * Call from UI thread.
     */
    public void sendSurfaceDestroyed(SurfaceTexture holder) {
        sendMessage(obtainMessage(MSG_SURFACE_DESTROYED, 0, 0, holder));
    }

    /**
     * Sends the "shutdown" message, which tells the render thread to halt.
     * <p>
     * Call from UI thread.
     */
    public void sendShutdown() {
        sendMessage(obtainMessage(MSG_SHUTDOWN));
    }

    /**
     * Sends the "frame available" message.
     * <p>
     * Call from UI thread.
     */
    public void sendFrameAvailable() {
        sendMessage(obtainMessage(MSG_FRAME_AVAILABLE));
    }


    public void sendSetFilter(GPUImageFilter filter) {
        sendMessage(obtainMessage(MSG_SET_FILTER, filter));
    }

    @Override  // runs on RenderThread
    public void handleMessage(Message msg) {
        int what = msg.what;
        //Log.i(TAG, "RenderHandler [" + this + "]: what=" + what);

        RenderThread renderThread = mWeakRenderThread.get();
        if (renderThread == null) {
            Log.w(TAG, "RenderHandler.handleMessage: weak ref is null");
            return;
        }

        switch (what) {
            case MSG_SURFACE_AVAILABLE:
                renderThread.surfaceAvailable((SurfaceTexture) msg.obj, msg.arg1, msg.arg2);
                break;
            case MSG_SURFACE_CHANGED:
                renderThread.surfaceChanged((SurfaceTexture) msg.obj, msg.arg1, msg.arg2);
                break;
            case MSG_SURFACE_DESTROYED:
                renderThread.surfaceDestroyed((SurfaceTexture) msg.obj);
                break;
            case MSG_SHUTDOWN:
                renderThread.shutdown();
                break;
            case MSG_FRAME_AVAILABLE:
                renderThread.frameAvailable();
                break;
            case MSG_REDRAW:
                renderThread.draw();
                break;
            case MSG_SET_FILTER:
                renderThread.setFilter((GPUImageFilter) msg.obj);
                break;
            default:
                throw new RuntimeException("unknown message " + what);
        }
    }
}