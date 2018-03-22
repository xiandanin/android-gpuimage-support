package com.dyhdyh.gpuimage.support.example;

import com.dyhdyh.gpuimage.support.example.adapter.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 给adapter赋予多选能力
 * author  dengyuhan
 * created 2017/8/7 15:45
 */
public class CheckAdapterHelper<Model> {
    private List<Model> mCheckedList = new ArrayList<>();
    private OnDataCheckedCallback mCallback;

    public CheckAdapterHelper(OnDataCheckedCallback callback) {
        this.mCallback = callback;
    }

    public void setCheckedPosition(BaseRecyclerAdapter adapter, int position, boolean checked) {
        mCallback.onDataChecked(position, checked);
        if (checked) {
            mCheckedList.add((Model) adapter.getData().get(position));
        } else {
            mCheckedList.remove(adapter.getData().get(position));
        }
        adapter.notifyItemChanged(position);
    }

    public List<Model> getCheckedList() {
        return mCheckedList;
    }

    public void clear() {
        mCheckedList.clear();
    }

    public interface OnDataCheckedCallback {
        void onDataChecked(int position, boolean checked);
    }


    public interface CheckAdapter<Model> {
        void setCheckedPosition(int position, boolean checked);

        List<Model> getCheckedList();
    }
}
