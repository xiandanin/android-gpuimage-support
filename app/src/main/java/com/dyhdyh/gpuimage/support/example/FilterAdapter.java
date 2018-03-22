package com.dyhdyh.gpuimage.support.example;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcssloop.widget.CheckedRCRelativeLayout;

import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImage;

/**
 * @author dengyuhan
 *         created 2018/3/21 19:16
 */
public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.Holder> implements RadioAdapterHelper.RadioAdapter {
    private List<FilterModel> mData;
    private GPUImage mGPUImage;
    private Bitmap mSrcBitmap;

    private SparseArray<Bitmap> mCacheBitmap;

    private RadioAdapterHelper mHelper;

    private OnItemClickListener<FilterModel> mOnItemClickListener;

    public FilterAdapter(Context context, List<FilterModel> data, Bitmap src) {
        this.mData = data;
        this.mSrcBitmap = src;
        this.mGPUImage = new GPUImage(context);
        this.mCacheBitmap = new SparseArray<>();
        this.mHelper = new RadioAdapterHelper(new RadioAdapterHelper.OnDataCheckedCallback() {
            @Override
            public void onDataChecked(int position, boolean checked) {
                mData.get(position).setChecked(checked);
            }
        });
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_list, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        final FilterModel item = mData.get(position);
        Bitmap filterBitmap = mCacheBitmap.get(position);
        if (filterBitmap == null) {
            mGPUImage.setFilter(item.getFilter());
            filterBitmap = mGPUImage.getBitmapWithFilterApplied(mSrcBitmap);
            mCacheBitmap.put(position, filterBitmap);
        }
        holder.rlContainer.setChecked(item.isChecked());
        holder.ivCover.setImageBitmap(filterBitmap);
        holder.tvName.setText(item.getFilterName());

        holder.rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(FilterAdapter.this, position, item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void setCheckedPosition(int checkedPosition) {
        mHelper.setCheckedPosition(this, checkedPosition);
    }

    @Override
    public int getCheckedPosition() {
        return mHelper.getCheckedPosition();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
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

    public interface OnItemClickListener<T> {
        void onItemClick(RecyclerView.Adapter adapter, int position, T item);
    }
}
