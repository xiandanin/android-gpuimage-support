package com.dyhdyh.gpuimage.support.example.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * @author dengyuhan
 *         created 2018/3/22 16:20
 */
public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private List<T> mData;
    private OnItemClickListener<T> mOnItemClickListener;

    public BaseRecyclerAdapter(List<T> data) {
        this.mData = data;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<T> getData() {
        return mData;
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        onBindViewHolder(holder, position, getData().get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(BaseRecyclerAdapter.this, position, mData.get(position));
                }
            }
        });
    }

    public abstract void onBindViewHolder(VH holder, final int position, T item);

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(BaseRecyclerAdapter recyclerAdapter, int position, T item);
    }
}
