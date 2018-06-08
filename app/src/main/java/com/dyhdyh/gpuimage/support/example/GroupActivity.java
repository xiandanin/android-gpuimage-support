package com.dyhdyh.gpuimage.support.example;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.dyhdyh.gpuimage.support.example.adapter.BaseFilterAdapter;
import com.dyhdyh.gpuimage.support.example.adapter.BaseRecyclerAdapter;
import com.dyhdyh.gpuimage.support.example.adapter.GroupAdapter;
import com.dyhdyh.gpuimage.support.example.model.FilterGroupExampleData;
import com.dyhdyh.gpuimage.support.example.model.FilterGroupModel;
import com.dyhdyh.gpuimage.support.example.model.FilterInfo;
import com.dyhdyh.gpuimage.support.example.util.FileUtils;
import com.dyhdyh.gpuimage.support.example.util.GPUImageCoverUtil;
import com.dyhdyh.gpuimage.support.example.view.FilterSeekLayout;
import com.dyhdyh.gpuimage.support.example.view.GPUImageTextureLayout;
import com.dyhdyh.gpuimage.support.rxjava2.GPUImageOutput;
import com.dyhdyh.gpuimage.support.rxjava2.GPUImageRxJava2Adapter;
import com.dyhdyh.subscribers.loadingbar.rxjava2.SimpleLoadingDialogObserver;

import java.io.File;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;

/**
 * @author dengyuhan
 *         created 2018/6/6 15:49
 */
public class GroupActivity extends AppCompatActivity implements BaseRecyclerAdapter.OnItemClickListener<FilterGroupModel> {
    @BindView(R.id.rv_filter_group)
    RecyclerView rv_filter;

    @BindView(R.id.rv_base_filter)
    RecyclerView rv_base_filter;

    @BindView(R.id.texture)
    GPUImageTextureLayout texture;

    @BindView(R.id.sb_filter)
    FilterSeekLayout seekLayout;

    private GPUImageCoverUtil mCoverUtil;

    private File inputFile;

    private GroupAdapter mGroupAdapter;
    private BaseFilterAdapter mBaseFilterAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        ButterKnife.bind(this);

        inputFile = new File(getExternalCacheDir(), "test.jpg");
        FileUtils.copyAssetFile(this, inputFile.getName(), inputFile);

        mCoverUtil = new GPUImageCoverUtil(this);
        mCoverUtil.setSourcePath(true, R.drawable.test, inputFile.getAbsolutePath());

        texture.setDataSource(inputFile.getAbsolutePath());

        setFilterGroupAdapter();
        setBaseFilterAdapter();

    }


    public void setFilterGroupAdapter() {
        final List<FilterGroupModel> groups = FilterGroupExampleData.all();
        mGroupAdapter = new GroupAdapter(groups);
        mGroupAdapter.setOnItemClickListener(this);
        rv_filter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_filter.setAdapter(mGroupAdapter);
    }

    public void setBaseFilterAdapter() {
        final List<FilterInfo> filtes = FilterGroupExampleData.baseFilters();
        mBaseFilterAdapter = new BaseFilterAdapter(filtes);
        //mBaseFilterAdapter.setOnItemClickListener(this);
        rv_base_filter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_base_filter.setAdapter(mBaseFilterAdapter);
    }

    @Override
    public void onItemClick(BaseRecyclerAdapter adapter, int position, FilterGroupModel item) {
        setGPUImageFilter(position);
    }

    private void setGPUImageFilter(int position) {
        final FilterGroupModel item = mGroupAdapter.getItem(position);
        mGroupAdapter.setCheckedPosition(position);
        GPUImageFilterGroup group = new GPUImageFilterGroup();
        if (item.getBrightness() != null) {
            final FilterInfo info = item.getBrightness();
            group.addFilter(new GPUImageBrightnessFilter(info.range()));
        }
        if (item.getSaturation() != null) {
            final FilterInfo info = item.getSaturation();
            group.addFilter(new GPUImageSaturationFilter(info.range()));
        }
        texture.setFilter(group);
        final List<FilterInfo> data = mBaseFilterAdapter.getData();
        for (FilterInfo info : data) {
            info.setProgress(new Random().nextFloat());
        }
        mBaseFilterAdapter.notifyItemRangeChanged(0, data.size(), true);
    }


    public void showFilterSeekBar(final int position) {
        /*final FilterModel item = mFilterAdapter.getItem(position);
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
        });*/
    }


    /**
     * 导出文件
     *
     * @param item
     */
    public void clickExportFile(MenuItem item) {
        File outputFile = new File(getExternalCacheDir(), "filter.jpg");
        new GPUImageOutput(texture.getGPUImage())
                .setOutputFormat(Bitmap.CompressFormat.JPEG)
                .setQuality(80)
                .setOutputFile(outputFile)
                .outputFilterBitmap(null, GPUImageRxJava2Adapter.<File>create())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleLoadingDialogObserver<File>(this) {
                    @Override
                    public void onNext(File file) {
                        super.onNext(file);
                        Toast.makeText(GroupActivity.this, "保存成功->" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
