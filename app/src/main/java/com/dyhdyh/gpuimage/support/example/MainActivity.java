package com.dyhdyh.gpuimage.support.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dyhdyh.gpuimage.support.example.adapter.BaseRecyclerAdapter;
import com.dyhdyh.gpuimage.support.example.adapter.FilterAdapter;
import com.dyhdyh.gpuimage.support.example.model.CoverBitmapModel;
import com.dyhdyh.gpuimage.support.example.model.FilterModel;
import com.dyhdyh.gpuimage.support.example.model.FilterExampleData;
import com.dyhdyh.gpuimage.support.example.util.GPUImageCoverUtil;
import com.dyhdyh.gpuimage.support.example.view.GPUImageTextureView;

import java.util.List;

import io.reactivex.functions.Consumer;
import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup;
import jp.co.cyberagent.android.gpuimage.adjuster.GPUImageAdjuster;

public class MainActivity extends AppCompatActivity implements BaseRecyclerAdapter.OnItemClickListener<FilterModel> {
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

    private int mLastSeekbarPosition;//最后显示seekbar的滤镜位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_filter = findViewById(R.id.rv_filter);
        mImageView = findViewById(R.id.image);
        mTextureView = findViewById(R.id.texture);
        sb_filter_adjust = findViewById(R.id.sb_filter_adjust);
        tv_filter_progress = findViewById(R.id.tv_filter_progress);
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
    }

    public void clickPlay(View view) {
        List<FilterModel> filters = FilterExampleData.all();

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

        mCoverUtil.asyncBindFilterCover(filters)
                .subscribe(new Consumer<CoverBitmapModel>() {
                    @Override
                    public void accept(CoverBitmapModel bitmap) throws Exception {
                        mFilterAdapter.getData().get(bitmap.getIndex()).setCoverBitmap(bitmap.getBitmap());
                        mFilterAdapter.notifyItemChanged(bitmap.getIndex());
                    }
                });

        mTextureView.prepare();
        mTextureView.start();
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
        mFilterAdapter.setCheckedPositionArray(new int[]{position}, checked);
        if (checked) {
            if (!mFilterGroup.getFilters().contains(item.getFilter())){
                mFilterGroup.addFilter(item.getFilter());
            }
        } else {
            mFilterGroup.getFilters().remove(item.getFilter());
        }
        mTextureView.setFilter(mFilterGroup);

        Log.i("------------>", mFilterGroup.getFilters()+"");
    }

    public void cancelFilterSeekBar() {
        layout_filter_adjust.setVisibility(View.GONE);
        setFilter(mLastSeekbarPosition, false);
    }

    public void setFilterSeekBarTitle(FilterModel item) {
        String title = getResources().getString(item.getFilterNameRes()) + " " + String.valueOf(item.getProgress());
        tv_filter_progress.setText(title);
    }

}
