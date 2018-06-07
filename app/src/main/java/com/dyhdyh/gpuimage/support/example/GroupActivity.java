package com.dyhdyh.gpuimage.support.example;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
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

        boolean isImage;
        String inputPath = getIntent().getStringExtra("input_path");
        if (TextUtils.isEmpty(inputPath)) {
            //没有路径就用测试文件
            isImage = new Random().nextBoolean();
            isImage = true;
            inputFile = isImage ? new File(getExternalCacheDir(), "test.jpg") :
                    new File(getExternalCacheDir(), "test.mp4");
            FileUtils.copyAssetFile(this, inputFile.getName(), inputFile);
        } else {
            isImage = inputPath.endsWith("mp4");
            inputFile = new File(inputPath);
        }

        if (isImage) {
            texture.setImage(BitmapFactory.decodeFile(inputFile.getAbsolutePath()));
        } else {
            /*texture.setDataSource(inputFile.getAbsolutePath());
            texture.start();*/
        }

        mCoverUtil = new GPUImageCoverUtil(this);
        mCoverUtil.setSourcePath(isImage, R.drawable.test, inputFile.getAbsolutePath());

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
            group.addFilter(new GPUImageBrightnessFilter(info.getDefaultValue()));
        }
        if (item.getSaturation() != null) {
            final FilterInfo info = item.getSaturation();
            group.addFilter(new GPUImageSaturationFilter(info.getDefaultValue()));
        }
        texture.setFilter(group);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_example, menu);
        return true;
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
