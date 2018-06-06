package com.dyhdyh.gpuimage.support.example;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dyhdyh.gpuimage.support.example.adapter.BaseRecyclerAdapter;
import com.dyhdyh.gpuimage.support.example.adapter.FilterAdapter;
import com.dyhdyh.gpuimage.support.example.model.CoverBitmapModel;
import com.dyhdyh.gpuimage.support.example.model.FilterExampleData;
import com.dyhdyh.gpuimage.support.example.model.FilterModel;
import com.dyhdyh.gpuimage.support.example.util.GPUImageCoverUtil;
import com.dyhdyh.gpuimage.support.example.view.GPUImageTextureView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.List;

import io.reactivex.functions.Consumer;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup;
import jp.co.cyberagent.android.gpuimage.adjuster.GPUImageAdjuster;

public class MainActivity extends AppCompatActivity implements BaseRecyclerAdapter.OnItemClickListener<FilterModel> {
    private final int REQUEST_CODE_CHOOSE = 200;

    GPUImageTextureView mTextureView;
    ImageView mImageView;
    RecyclerView rv_filter;
    SeekBar sb_filter_adjust;
    TextView tv_filter_progress;
    View layout_filter_adjust;

    private GPUImageFilterGroup mFilterGroup;
    private GPUImageAdjuster mFilterAdjuster;

    private FilterAdapter mFilterAdapter;
    private GPUImageCoverUtil mCoverUtil;
    private Bitmap mSrcBitmap;
    private GPUImage mGPUImage;

    private int mLastSeekbarPosition;//最后显示seekbar的滤镜位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_filter = findViewById(R.id.rv_filter);
        mImageView = findViewById(R.id.image);
        mTextureView = findViewById(R.id.texture);
        sb_filter_adjust = findViewById(R.id.sb_filter_adjust);
        tv_filter_progress = findViewById(R.id.tv_filter_title);
        layout_filter_adjust = findViewById(R.id.layout_filter_adjust);

        findViewById(R.id.tv_filter_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelFilterSeekBar();
            }
        });

        findViewById(R.id.tv_filter_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_filter_adjust.setVisibility(View.GONE);
            }
        });

        mCoverUtil = new GPUImageCoverUtil(this);
        mFilterGroup = new GPUImageFilterGroup();
        mGPUImage = new GPUImage(this);

        setVideoSource(null);

        finish();
    }

    @Override
    public void onItemClick(BaseRecyclerAdapter recyclerAdapter, final int position, final FilterModel item) {
        mFilterAdjuster = item.getAdjuster();
        if (mFilterAdjuster == null) {
            layout_filter_adjust.setVisibility(View.GONE);
            //没有调整器就直接设置
            boolean checked = !item.isChecked();
            setFilter(position, checked);
        } else {
            showFilterSeekBar(position);
        }
    }


    public void showFilterSeekBar(final int position) {
        final FilterModel item = mFilterAdapter.getItem(position);
        mLastSeekbarPosition = position;
        layout_filter_adjust.setVisibility(View.VISIBLE);

        setFilter(position, true);

        setFilterSeekBarTitle(item);

        sb_filter_adjust.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                GPUImageAdjuster adjuster = item.getAdjuster();
                if (adjuster != null) {
                    adjuster.adjust(progress);
                    if (mSrcBitmap != null && mImageView.getVisibility() == View.VISIBLE) {
                        mImageView.setImageBitmap(mGPUImage.getBitmapWithFilterApplied(mSrcBitmap));
                    }
                }
                item.setProgress(progress);
                setFilterSeekBarTitle(item);
                mFilterAdapter.notifyItemChanged(position);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb_filter_adjust.setProgress(item.getProgress());
    }

    public void setFilter(int position, boolean checked) {
        FilterModel item = mFilterAdapter.getItem(position);
        //mFilterAdapter.setCheckedPositionArray(new int[]{position}, checked);
        if (checked) {
            if (!mFilterGroup.getFilters().contains(item.getFilter())) {
                mFilterGroup.addFilter(item.getFilter());
            }
        } else {
            mFilterGroup.getFilters().remove(item.getFilter());
        }
        mTextureView.setFilter(mFilterGroup);

        if (mImageView.getVisibility() == View.VISIBLE) {
            mGPUImage.setFilter(mFilterGroup);
            mImageView.setImageBitmap(mGPUImage.getBitmapWithFilterApplied(mSrcBitmap));
        }

        Log.i("------------>", mFilterGroup.getFilters() + "");
    }

    public void cancelFilterSeekBar() {
        layout_filter_adjust.setVisibility(View.GONE);
        setFilter(mLastSeekbarPosition, false);
    }

    public void setFilterSeekBarTitle(FilterModel item) {
        String title = getResources().getString(item.getFilterNameRes()) + " " + String.valueOf(item.getProgress());
        tv_filter_progress.setText(title);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void clickVideo(MenuItem item) {
        setVideoSource(null);
    }

    public void clickImage(MenuItem item) {
        setImageSource(null);
    }

    public void clickAlbum(MenuItem item) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            return;
        }
        Matisse.from(MainActivity.this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.BMP, MimeType.MP4))
                .countable(false)
                .spanCount(3)
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    public void clickReset(MenuItem item) {
        if (mFilterAdapter != null) {
            mFilterAdapter.clear();
        }
        mFilterGroup.getFilters().clear();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //从相册选择了图片或者视频
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            String source = Matisse.obtainPathResult(data).get(0);
            if (source.endsWith(".mp4")) {
                setVideoSource(source);
            } else {
                setImageSource(source);
            }
        }
    }


    /**
     * 图片
     *
     * @param path
     */
    public void setImageSource(String path) {
        mTextureView.pause();
        mTextureView.setVisibility(View.GONE);
        mImageView.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(path)) {
            mSrcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        } else {
            mSrcBitmap = BitmapFactory.decodeFile(path);
        }
        mImageView.setImageBitmap(mSrcBitmap);
        mCoverUtil.setSourcePath(true, R.drawable.test, path);

        setFilterAdapter();
    }

    /**
     * 视频
     *
     * @param path
     */
    public void setVideoSource(String path) {
        mImageView.setVisibility(View.GONE);
        mTextureView.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(path)) {
            mTextureView.setDataSource(R.raw.test);
        } else {
            mTextureView.setDataSource(path);
        }
        mCoverUtil.setSourcePath(false, R.raw.test, path);
        mTextureView.prepare();
        mTextureView.start();

        setFilterAdapter();
    }

    /**
     * 滤镜列表
     */
    public void setFilterAdapter() {
        List<FilterModel> filters;
        if (mFilterAdapter == null) {
            filters = FilterExampleData.all();

            //((SimpleItemAnimator) rv_filter.getItemAnimator()).setSupportsChangeAnimations(false);
            mFilterAdapter = new FilterAdapter(filters);
            mFilterAdapter.setOnItemClickListener(this);
            final GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (mFilterAdapter.getItemViewType(position) != 0) {
                        return layoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
            rv_filter.setLayoutManager(layoutManager);
            rv_filter.setAdapter(mFilterAdapter);
        } else {
            filters = mFilterAdapter.getData();
        }
        mCoverUtil.asyncBindFilterCover(filters)
                .subscribe(new Consumer<CoverBitmapModel>() {
                    @Override
                    public void accept(CoverBitmapModel bitmap) throws Exception {
                        mFilterAdapter.getData().get(bitmap.getIndex()).setCoverBitmap(bitmap.getBitmap());
                        mFilterAdapter.notifyItemChanged(bitmap.getIndex());
                    }
                });
    }
}
