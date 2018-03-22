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
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImage;

/**
 * @author dengyuhan
 *         created 2018/3/21 19:16
 */
public class FilterAdapter extends BaseRecyclerAdapter<FilterModel, RecyclerView.ViewHolder> implements CheckAdapterHelper.CheckAdapter<FilterModel>, StickyRecyclerHeadersAdapter<FilterAdapter.HeaderHolder> {
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
                getData().get(position).setChecked(checked);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return getData().get(position).isFilterGroupLabel() ? 1 : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_list, parent, false));
        } else {
            return new HeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_header, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, FilterModel item) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == 0) {
            ItemHolder holder = (ItemHolder) viewHolder;
            Bitmap filterBitmap = mCacheBitmap.get(position);
            if (filterBitmap == null) {
                mGPUImage.setFilter(item.getFilter());
                filterBitmap = mGPUImage.getBitmapWithFilterApplied(mSrcBitmap);
                mCacheBitmap.put(position, filterBitmap);
            }
            holder.rlContainer.setChecked(item.isChecked());
            holder.ivCover.setImageBitmap(filterBitmap);
            holder.tvName.setText(item.getFilterNameRes());
        } else {
            HeaderHolder holder = (HeaderHolder) viewHolder;
            holder.tvFilterGroup.setText(item.getFilterGroupNameRes());
        }

    }


    @Override
    public void setCheckedPosition(int position, boolean checked) {
        mHelper.setCheckedPosition(this, position, checked);
    }

    @Override
    public List<FilterModel> getCheckedList() {
        return mHelper.getCheckedList();
    }


    @Override
    public long getHeaderId(int position) {
        return getData().get(position).getFilterGroupNameRes();
    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new HeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_header, parent, false));
    }

    @Override
    public void onBindHeaderViewHolder(HeaderHolder holder, int position) {
        FilterModel filterModel = getData().get(position);
        holder.tvFilterGroup.setText(filterModel.getFilterGroupNameRes());
    }

    static class HeaderHolder extends RecyclerView.ViewHolder {
        TextView tvFilterGroup;

        public HeaderHolder(View itemView) {
            super(itemView);
            tvFilterGroup = itemView.findViewById(R.id.tv_filter_group);
        }
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        CheckedRCRelativeLayout rlContainer;
        ImageView ivCover;
        TextView tvName;

        public ItemHolder(View itemView) {
            super(itemView);
            rlContainer = itemView.findViewById(R.id.rl_container);
            ivCover = itemView.findViewById(R.id.iv_filter_cover);
            tvName = itemView.findViewById(R.id.tv_filter_name);
        }
    }
}
