package com.dyhdyh.gpuimage.support.example;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dyhdyh.gpuimage.support.example.adapter.BaseRecyclerAdapter;
import com.dyhdyh.gpuimage.support.example.adapter.FilterAdapter;
import com.dyhdyh.gpuimage.support.example.model.FilterModel;
import com.dyhdyh.gpuimage.support.example.model.FilterViewData;
import com.dyhdyh.gpuimage.support.example.view.GPUImageTextureView;

import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    GPUImageTextureView mTextureView;
    RecyclerView rv_filter;
    SeekBar sb_filter_adjust;
    TextView tv_filter_progress;

    private GPUImageFilterGroup mFilterGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_filter = findViewById(R.id.rv_filter);
        mTextureView = findViewById(R.id.texture);
        sb_filter_adjust = findViewById(R.id.sb_filter_adjust);
        sb_filter_adjust.setOnSeekBarChangeListener(this);
        tv_filter_progress = findViewById(R.id.tv_filter_progress);

        mFilterGroup = new GPUImageFilterGroup();
    }

    public void clickPlay(View view) {
        final MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.test));
        Bitmap atTime = retriever.getFrameAtTime();

        List<FilterModel> filters = FilterViewData.all();

        ((SimpleItemAnimator) rv_filter.getItemAnimator()).setSupportsChangeAnimations(false);
        final FilterAdapter filterAdapter = new FilterAdapter(this, filters, atTime);
        filterAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<FilterModel>() {
            @Override
            public void onItemClick(BaseRecyclerAdapter recyclerAdapter, int position, FilterModel item) {
                boolean checked = !item.isChecked();
                filterAdapter.setCheckedPosition(position, checked);
                if (checked) {
                    mFilterGroup.addFilter(item.getFilter());
                } else {
                    mFilterGroup.getFilters().remove(item.getFilter());
                }
                mTextureView.setFilter(mFilterGroup);
            }
        });
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (filterAdapter.getItemViewType(position) != 0) {
                    return layoutManager.getSpanCount();
                }
                return 1;
            }
        });
        rv_filter.setLayoutManager(layoutManager);
        rv_filter.setAdapter(filterAdapter);

        mTextureView.prepare();
        mTextureView.start();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        tv_filter_progress.setText();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
