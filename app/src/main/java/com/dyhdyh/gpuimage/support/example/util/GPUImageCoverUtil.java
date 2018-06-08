package com.dyhdyh.gpuimage.support.example.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.text.TextUtils;

import com.dyhdyh.gpuimage.support.example.R;
import com.dyhdyh.gpuimage.support.example.model.CoverBitmapModel;
import com.dyhdyh.gpuimage.support.example.model.FilterModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jp.co.cyberagent.android.gpuimage.GPUImage;

/**
 * @author dengyuhan
 *         created 2018/3/26 10:38
 */
public class GPUImageCoverUtil {
    private Context mContext;
    private boolean mImage;
    private int mTestResId;
    private String mSourcePath;

    public GPUImageCoverUtil(Context context) {
        this.mContext = context;
    }


    public void setSourcePath(boolean image, int testResId, String sourcePath) {
        this.mImage = image;
        this.mTestResId = testResId;
        this.mSourcePath = sourcePath;
    }

    /**
     * 获取视频封面图
     *
     * @return
     */
    private Bitmap getSrcBitmap() {
        int width = mContext.getResources().getDimensionPixelSize(R.dimen.width_filter_cover);
        int height = mContext.getResources().getDimensionPixelSize(R.dimen.height_filter_cover);
        Bitmap bitmap;
        //如果没有路径就使用测试文件
        if (mImage) {
            bitmap = TextUtils.isEmpty(mSourcePath) ? BitmapFactory.decodeResource(mContext.getResources(), mTestResId) : BitmapFactory.decodeFile(mSourcePath);
        } else {
            final MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            if (TextUtils.isEmpty(mSourcePath)) {
                retriever.setDataSource(mContext, Uri.parse("android.resource://" + mContext.getPackageName() + "/" + mTestResId));
            } else {
                retriever.setDataSource(mSourcePath);
            }
            bitmap = retriever.getFrameAtTime();
        }
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    /**
     * 异步给封面图加滤镜
     *
     * @param filters
     * @return
     */
    public Observable<CoverBitmapModel> asyncBindFilterCover(final List<FilterModel> filters) {
        final GPUImage gpuImage = new GPUImage(mContext);
        return Observable.create(new ObservableOnSubscribe<CoverBitmapModel>() {
            @Override
            public void subscribe(ObservableEmitter<CoverBitmapModel> emitter) throws Exception {
                try {
                    Bitmap srcBitmap = getSrcBitmap();
                    for (int i = 0; i < filters.size(); i++) {
                        FilterModel filter = filters.get(i);
                        if (filter.getFilter() != null) {
                            gpuImage.setFilter(filter.getFilter());
                            Bitmap filterBitmap = gpuImage.getBitmapWithFilterApplied(srcBitmap);
                            emitter.onNext(new CoverBitmapModel(i, filterBitmap));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
