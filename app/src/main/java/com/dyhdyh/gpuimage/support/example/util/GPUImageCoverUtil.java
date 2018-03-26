package com.dyhdyh.gpuimage.support.example.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;

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

    public GPUImageCoverUtil(Context context) {
        this.mContext = context;
    }

    /**
     * 获取视频封面图
     *
     * @return
     */
    public Bitmap getSrcBitmap() {
        int width = mContext.getResources().getDimensionPixelSize(R.dimen.width_filter_cover);
        int height = mContext.getResources().getDimensionPixelSize(R.dimen.height_filter_cover);
        final MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(mContext, Uri.parse("android.resource://" + mContext.getPackageName() + "/" + R.raw.test));
        Bitmap atTime = retriever.getFrameAtTime();
        return Bitmap.createScaledBitmap(atTime, width, height, true);
    }

    /**
     * 异步给封面图加滤镜
     *
     * @param filters
     * @return
     */
    public Observable<CoverBitmapModel> asyncBindFilterCover(final List<FilterModel> filters) {
        return Observable.create(new ObservableOnSubscribe<CoverBitmapModel>() {
            @Override
            public void subscribe(ObservableEmitter<CoverBitmapModel> emitter) throws Exception {
                try {
                    Bitmap srcBitmap = getSrcBitmap();

                    GPUImage gpuImage = new GPUImage(mContext);

                    for (int i = 0; i < filters.size(); i++) {
                        FilterModel filter = filters.get(i);
                        if (filter.getFilter() != null) {
                            gpuImage.setFilter(filter.getFilter());
                            Bitmap filterBitmap = gpuImage.getBitmapWithFilterApplied(srcBitmap);
                            emitter.onNext(new CoverBitmapModel(i, filterBitmap));
                        }
                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
