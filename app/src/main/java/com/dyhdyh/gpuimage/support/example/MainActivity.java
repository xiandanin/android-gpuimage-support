package com.dyhdyh.gpuimage.support.example;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;

import com.dyhdyh.gpuimage.support.example.view.GPUImageTextureView;

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

        ((SimpleItemAnimator) rv_filter.getItemAnimator()).setSupportsChangeAnimations(false);
        final FilterAdapter filterAdapter = new FilterAdapter(this, FilterModel.generateAll(), atTime);
        filterAdapter.setOnItemClickListener(new FilterAdapter.OnItemClickListener<FilterModel>() {
            @Override
            public void onItemClick(RecyclerView.Adapter adapter, int position, FilterModel item) {
                filterAdapter.setCheckedPosition(position);
                mTextureView.setFilter(item.getFilter());
            }
        });
        rv_filter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_filter.setAdapter(filterAdapter);

        mTextureView.prepare();
        mTextureView.start();

    }
}
