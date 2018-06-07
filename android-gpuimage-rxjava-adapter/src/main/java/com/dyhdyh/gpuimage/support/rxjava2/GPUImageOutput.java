package com.dyhdyh.gpuimage.support.rxjava2;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import jp.co.cyberagent.android.gpuimage.GPUImage;

/**
 * @author dengyuhan
 *         created 2018/6/6 18:06
 */
public class GPUImageOutput {
    public static final Bitmap.CompressFormat DEFAULT_OUTPUT_FORMAT = Bitmap.CompressFormat.PNG;
    public static final int DEFAULT_QUALITY = 100;

    private GPUImage mGPUImage;
    private Bitmap.CompressFormat mCompressFormat = DEFAULT_OUTPUT_FORMAT;
    private int mQuality = DEFAULT_QUALITY;
    private File mOutputFile;

    public GPUImageOutput(GPUImage gpuImage) {
        this.mGPUImage = gpuImage;
    }

    public GPUImageOutput setOutputFormat(Bitmap.CompressFormat format) {
        this.mCompressFormat = format;
        return this;
    }

    public GPUImageOutput setQuality(int quality) {
        this.mQuality = quality;
        return this;
    }

    public GPUImageOutput setOutputFile(File outputFile) {
        this.mOutputFile = outputFile;
        return this;
    }

    public GPUImage getGPUImage() {
        return mGPUImage;
    }

    public <Observable> Observable getFilterBitmap(final Bitmap srcBitmap, GPUImageRxJavaAdapter<Observable, Bitmap> adapter) {
        return adapter.asObservable(new FunctionDelegate<Bitmap>() {
            @Override
            public Bitmap run() {
                if (srcBitmap == null) {
                    return mGPUImage.getBitmapWithFilterApplied();
                } else {
                    return mGPUImage.getBitmapWithFilterApplied(srcBitmap);
                }

            }
        });
    }

    public <Observable> Observable outputFilterBitmap(final Bitmap srcBitmap, GPUImageRxJavaAdapter<Observable, File> adapter) {
        return adapter.asObservable(new FunctionDelegate<File>() {
            @Override
            public File run() throws FileNotFoundException {
                final Bitmap filterBitmap;
                if (srcBitmap == null) {
                    filterBitmap = mGPUImage.getBitmapWithFilterApplied();
                } else {
                    filterBitmap = mGPUImage.getBitmapWithFilterApplied(srcBitmap);
                }
                filterBitmap.compress(mCompressFormat, mQuality, new FileOutputStream(mOutputFile));
                return mOutputFile;
            }
        });
    }
}