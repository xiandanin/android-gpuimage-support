package com.dyhdyh.gpuimage.support.example;

import android.support.v7.widget.RecyclerView;

/**
 * 给adapter赋予单选能力
 * author  dengyuhan
 * created 2017/8/7 15:45
 */
public class RadioAdapterHelper {
    private int mCheckedPosition = -1;
    private OnDataCheckedCallback mCallback;

    public RadioAdapterHelper(OnDataCheckedCallback callback) {
        this.mCallback = callback;
    }

    public void setCheckedPosition(RecyclerView.Adapter adapter, int checkedPosition) {
        try {
            if (mCheckedPosition >= 0) {
                mCallback.onDataChecked(mCheckedPosition, false);
                adapter.notifyItemChanged(mCheckedPosition);
            }
        } catch (IndexOutOfBoundsException ignored) {

        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mCheckedPosition = checkedPosition;
        try {
            mCallback.onDataChecked(mCheckedPosition, true);
            adapter.notifyItemChanged(mCheckedPosition);
        } catch (IndexOutOfBoundsException ignored) {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCheckedPosition() {
        return mCheckedPosition;
    }

    public interface OnDataCheckedCallback {
        void onDataChecked(int position, boolean checked);
    }


    public interface RadioAdapter {
        void setCheckedPosition(int checkedPosition);

        int getCheckedPosition();
    }
}
