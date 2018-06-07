package com.dyhdyh.gpuimage.support.example.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dyhdyh.gpuimage.support.example.R;
import com.dyhdyh.gpuimage.support.example.model.FilterGroupModel;
import com.dyhdyh.helper.checkable.SingleCheckableAdapter;
import com.dyhdyh.helper.checkable.SingleCheckableHelper;

import java.util.List;

/**
 * @author dengyuhan
 *         created 2018/6/7 20:03
 */
public class GroupAdapter extends BaseRecyclerAdapter<FilterGroupModel, GroupAdapter.ItemHolder> implements SingleCheckableAdapter {
    private SingleCheckableHelper mHelper;

    public GroupAdapter(List<FilterGroupModel> data) {
        super(data);
        mHelper = new SingleCheckableHelper(this);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position, FilterGroupModel item) {
        holder.tv_group_label.setText(item.getGroupName());
        holder.tv_group_label.setTextColor(item.isChecked() ? Color.RED : Color.BLACK);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_group, parent, false));
    }

    @Override
    public void setCheckedPosition(Integer checkedPosition) {
        mHelper.setCheckedPosition(checkedPosition);
    }

    @Override
    public Integer getCheckedPosition() {
        return mHelper.getCheckedPosition();
    }

    @Override
    public void onAdapterNotifyChanged(int[] checkedPositionArray) {
        notifyItemChanged(checkedPositionArray[0]);
    }

    @Override
    public void onChecked(int checkedPosition, boolean checked) {
        getData().get(checkedPosition).setChecked(checked);
    }

    @Override
    public void clear() {
        mHelper.clear();
    }


    static class ItemHolder extends RecyclerView.ViewHolder {
        TextView tv_group_label;

        public ItemHolder(View itemView) {
            super(itemView);
            tv_group_label = itemView.findViewById(R.id.tv_group_label);
        }
    }
}
