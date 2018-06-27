package com.dyhdyh.gpuimage.support.example;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.dyhdyh.gpuimage.support.example.util.FileUtils;
import com.dyhdyh.gpuimage.support.example.util.GPUImageCoverUtil;
import com.dyhdyh.gpuimage.support.example.view.FilterSeekLayout;
import com.dyhdyh.gpuimage.support.example.view.GPUImageTextureLayout;
import com.dyhdyh.gpuimage.support.rxjava2.GPUImageOutput;
import com.dyhdyh.gpuimage.support.rxjava2.GPUImageRxJava2Adapter;
import com.dyhdyh.subscribers.loadingbar.rxjava2.SimpleLoadingDialogObserver;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.adjuster.GPUImageAdjuster;

/**
 * @author dengyuhan
 *         created 2018/6/6 15:49
 */
public class FilterActivity extends AppCompatActivity implements BaseRecyclerAdapter.OnItemClickListener<FilterModel> {
    private static final int REQUEST_CODE_CHOOSE = 100;
    @BindView(R.id.rv_filter)
    RecyclerView rv_filter;

    @BindView(R.id.texture)
    GPUImageTextureLayout texture;

    @BindView(R.id.sb_filter)
    FilterSeekLayout seekLayout;

    private FilterAdapter mFilterAdapter;
    private GPUImageCoverUtil mCoverUtil;

    private File inputFile;

    private GPUImageFilter mFilter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        ButterKnife.bind(this);

        String inputPath = getIntent().getStringExtra("input_path");
        boolean testImage = getIntent().getBooleanExtra("test_image", true);
        if (TextUtils.isEmpty(inputPath)) {
            //没有路径就用测试文件
            inputFile = testImage ? new File(getExternalCacheDir(), "test1.jpg") :
                    new File(getExternalCacheDir(), "test.mp4");
            FileUtils.copyAssetFile(this, inputFile.getName(), inputFile);
        } else {
            testImage = !inputPath.endsWith("mp4");
            inputFile = new File(inputPath);
        }

        texture.setDataSource(inputFile.getAbsolutePath());
        if (!testImage) {
            texture.start();
        }

        mCoverUtil = new GPUImageCoverUtil(this);
        mCoverUtil.setSourcePath(testImage, inputFile.getAbsolutePath());

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
        mFilter = item.getFilter();
        texture.setFilter(mFilter);
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
        return true;
    }

    /**
     * 测试视频
     *
     * @param item
     */
    public void clickTestVideo(MenuItem item) {
        startFilterActivity(false);
    }

    /**
     * 测试图片
     *
     * @param item
     */
    public void clickTestImage(MenuItem item) {
        startFilterActivity(true);
    }

    /**
     * 从相册选择
     *
     * @param item
     */
    public void clickAlbum(MenuItem item) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            return;
        }
        Matisse.from(this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.BMP, MimeType.MP4))
                .countable(false)
                .spanCount(3)
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    /**
     * 导出文件
     *
     * @param item
     */
    public void clickExportFile(MenuItem item) {
        File outputFile = new File(getExternalCacheDir(), "filter.jpg");
        new GPUImageOutput(texture.getGPUImage())
                .setFilterByClass(mFilter)
                .setOutputFormat(Bitmap.CompressFormat.JPEG)
                .setQuality(80)
                .setOutputFile(outputFile)
                .outputFilterBitmap(GPUImageRxJava2Adapter.<File>create())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleLoadingDialogObserver<File>(this) {
                    @Override
                    public void onNext(File file) {
                        super.onNext(file);
                        Toast.makeText(FilterActivity.this, "保存成功->" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //从相册选择了图片或者视频
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            String source = Matisse.obtainPathResult(data).get(0);
            if (TextUtils.isEmpty(source)) {
                Toast.makeText(this, "选择的是路径是空的", Toast.LENGTH_SHORT).show();
                return;
            }
            startFilterActivity(source);
        }
    }

    private void startFilterActivity(String source) {
        Intent intent = new Intent(this, FilterActivity.class);
        intent.putExtra("input_path", source);
        startActivity(intent);
        finish();
    }

    private void startFilterActivity(boolean image) {
        Intent intent = new Intent(this, FilterActivity.class);
        intent.putExtra("test_image", image);
        startActivity(intent);
        finish();
    }
}
