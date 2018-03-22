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

import com.dyhdyh.gpuimage.support.example.adapter.BaseRecyclerAdapter;
import com.dyhdyh.gpuimage.support.example.adapter.FilterAdapter;
import com.dyhdyh.gpuimage.support.example.model.FilterModel;
import com.dyhdyh.gpuimage.support.example.model.FilterViewData;
import com.dyhdyh.gpuimage.support.example.view.GPUImageTextureView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    GPUImageTextureView mTextureView;
    RecyclerView rv_filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv_filter = findViewById(R.id.rv_filter);

        mTextureView = findViewById(R.id.texture);
        //mTextureView.setFilter(new GPUImageBulgeDistortionFilter());
    }

    public void clickPlay(View view) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.test));
        Bitmap atTime = retriever.getFrameAtTime();

        List<FilterModel> filters = FilterViewData.all();

        ((SimpleItemAnimator) rv_filter.getItemAnimator()).setSupportsChangeAnimations(false);
        final FilterAdapter filterAdapter = new FilterAdapter(this, filters, atTime);
        filterAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<FilterModel>() {
            @Override
            public void onItemClick(BaseRecyclerAdapter recyclerAdapter, int position, FilterModel item) {
                filterAdapter.setCheckedPosition(position, true);
                mTextureView.setFilter(item.getFilter());
            }
        });
        rv_filter.setLayoutManager(new GridLayoutManager(this, 5));
        rv_filter.setAdapter(filterAdapter);

        mTextureView.prepare();
        mTextureView.start();

    }
}
