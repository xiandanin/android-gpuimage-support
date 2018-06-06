package com.dyhdyh.gpuimage.support.example.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dyhdyh.gpuimage.support.example.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author dengyuhan
 *         created 2018/6/6 16:46
 */
public class FilterSeekLayout extends RelativeLayout {

    @BindView(R.id.tv_filter_title)
    TextView tv_filter_title;

    @BindView(R.id.sb_filter_adjust)
    SeekBar sb_filter_adjust;


    public FilterSeekLayout(@NonNull Context context) {
        this(context, null);
    }

    public FilterSeekLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterSeekLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.layout_filter_seek, this);
        ButterKnife.bind(this);
    }

    public void setFilterTitle(String title) {
        tv_filter_title.setText(title);
    }

    public void setProgress(int progress) {
        sb_filter_adjust.setProgress(progress);
    }

    public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener listener) {
        sb_filter_adjust.setOnSeekBarChangeListener(listener);

    }

    @OnClick(R.id.tv_filter_cancel)
    public void clickCancel() {
        setVisibility(GONE);
    }

    @OnClick(R.id.tv_filter_confirm)
    public void clickConfirm() {
        setVisibility(GONE);
    }

}
