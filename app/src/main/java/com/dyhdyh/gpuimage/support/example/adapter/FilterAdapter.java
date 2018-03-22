package com.dyhdyh.gpuimage.support.example.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyhdyh.gpuimage.support.example.CheckAdapterHelper;
import com.dyhdyh.gpuimage.support.example.R;
import com.dyhdyh.gpuimage.support.example.model.FilterModel;
import com.gcssloop.widget.CheckedRCRelativeLayout;

import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImage;

/**
 * @author dengyuhan
 *         created 2018/3/21 19:16
 */
public class FilterAdapter extends BaseRecyclerAdapter<FilterModel, FilterAdapter.Holder> implements CheckAdapterHelper.CheckAdapter<FilterModel>{
    private GPUImage mGPUImage;
    private Bitmap mSrcBitmap;

    private SparseArray<Bitmap> mCacheBitmap;

    private CheckAdapterHelper mHelper;

    public FilterAdapter(Context context, List<FilterModel> data, Bitmap src) {
        super(data);
        this.mSrcBitmap = src;
        this.mGPUImage = new GPUImage(context);
        this.mCacheBitmap = new SparseArray<>();
        this.mHelper = new CheckAdapterHelper(new CheckAdapterHelper.OnDataCheckedCallback() {
            @Override
            public void onDataChecked(int position, boolean checked) {
                //getData().get(position).setChecked(checked);
            }
        });
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_list, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position, FilterModel item) {
        Bitmap filterBitmap = mCacheBitmap.get(position);
        if (filterBitmap == null) {
            mGPUImage.setFilter(item.getFilter());
            filterBitmap = mGPUImage.getBitmapWithFilterApplied(mSrcBitmap);
            mCacheBitmap.put(position, filterBitmap);
        }
        holder.rlContainer.setChecked(item.isChecked());
        holder.ivCover.setImageBitmap(filterBitmap);
        holder.tvName.setText(item.getFilterNameRes());
    }


    @Override
    public void setCheckedPosition(int position, boolean checked) {
        mHelper.setCheckedPosition(this, position, checked);
    }

    @Override
    public List<FilterModel> getCheckedList() {
        return null;
    }

    static class Holder extends RecyclerView.ViewHolder {
        CheckedRCRelativeLayout rlContainer;
        ImageView ivCover;
        TextView tvName;

        public Holder(View itemView) {
            super(itemView);
            rlContainer = itemView.findViewById(R.id.rl_container);
            ivCover = itemView.findViewById(R.id.iv_filter_cover);
            tvName = itemView.findViewById(R.id.tv_filter_name);
        }
    }
}
