package com.dyhdyh.gpuimage.support.example;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.dyhdyh.gpuimage.support.example.adapter.BaseRecyclerAdapter;
import com.dyhdyh.gpuimage.support.example.adapter.FilterAdapter;
import com.dyhdyh.gpuimage.support.example.model.CoverBitmapModel;
import com.dyhdyh.gpuimage.support.example.model.FilterExampleData;
import com.dyhdyh.gpuimage.support.example.model.FilterModel;
import com.dyhdyh.gpuimage.support.example.util.GPUImageCoverUtil;
import com.dyhdyh.gpuimage.support.example.view.FilterSeekLayout;
import com.dyhdyh.gpuimage.support.example.view.GPUImageTextureLayout;
import com.dyhdyh.gpuimage.support.rxjava2.GPUImageOutput;
import com.dyhdyh.gpuimage.support.rxjava2.GPUImageRxJava2Adapter;
import com.dyhdyh.subscribers.loadingbar.rxjava2.SimpleLoadingDialogObserver;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import jp.co.cyberagent.android.gpuimage.adjuster.GPUImageAdjuster;

/**
 * @author dengyuhan
 *         created 2018/6/6 15:49
 */
public class ExampleActivity extends AppCompatActivity implements BaseRecyclerAdapter.OnItemClickListener<FilterModel> {
    @BindView(R.id.rv_filter)
    RecyclerView rv_filter;

    @BindView(R.id.texture)
    GPUImageTextureLayout texture;

    @BindView(R.id.sb_filter)
    FilterSeekLayout seekLayout;

    private FilterAdapter mFilterAdapter;
    private GPUImageCoverUtil mCoverUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        ButterKnife.bind(this);

        final File inputFile = new File(Environment.getExternalStorageDirectory(), "zhuangjia.jpg");
        texture.setImage(BitmapFactory.decodeFile(inputFile.getAbsolutePath()));

        mCoverUtil = new GPUImageCoverUtil(this);
        mCoverUtil.setSourcePath(true, R.drawable.test, inputFile.getAbsolutePath());

        setFilterAdapter();
    }


    /**
     * 滤镜列表
     */
    public void setFilterAdapter() {
        List<FilterModel> filters;
        if (mFilterAdapter == null) {
            filters = FilterExampleData.allNoTitle();

            //((SimpleItemAnimator) rv_filter.getItemAnimator()).setSupportsChangeAnimations(false);
            mFilterAdapter = new FilterAdapter(filters);
            mFilterAdapter.setOnItemClickListener(this);
            rv_filter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
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

    private void setGPUImageFilter(int position) {
        final FilterModel item = mFilterAdapter.getItem(position);
        mFilterAdapter.setCheckedPosition(position);
        texture.setFilter(item.getFilter());
    }

    @Override
    public void onItemClick(BaseRecyclerAdapter recyclerAdapter, int position, FilterModel item) {
        //如果有调整器 并且已经选中
        boolean showFilterBar = item.isChecked() && item.getAdjuster() != null;
        if (showFilterBar) {
            showFilterSeekBar(position);
        } else {
            seekLayout.setVisibility(View.GONE);
            setGPUImageFilter(position);
        }
        clickExportFile(null);
    }

    public void showFilterSeekBar(final int position) {
        final FilterModel item = mFilterAdapter.getItem(position);
        seekLayout.setVisibility(View.VISIBLE);
        seekLayout.setFilterTitle(getResources().getString(item.getFilterNameRes()));
        seekLayout.setProgress(item.getProgress());
        seekLayout.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                item.setProgress(progress);
                GPUImageAdjuster adjuster = item.getAdjuster();
                if (adjuster != null) {
                    adjuster.adjust(progress);
                    texture.requestRender();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_example, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 导出文件
     *
     * @param item
     */
    public void clickExportFile(MenuItem item) {
        File outputFile = new File(getExternalCacheDir(), "filter.png");
        new GPUImageOutput(texture.getGPUImage())
                .setOutputFile(outputFile)
                .outputFilterBitmap(null, GPUImageRxJava2Adapter.<File>create())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleLoadingDialogObserver<File>(this){
                    @Override
                    public void onNext(File file) {
                        super.onNext(file);
                        Toast.makeText(ExampleActivity.this, "保存成功->"+file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
