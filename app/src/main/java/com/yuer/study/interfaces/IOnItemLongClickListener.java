package com.yuer.study.interfaces;

import android.view.View;

/**
 * Created by Meiji on 2016/12/13.
 */

public interface IOnItemLongClickListener {

    /**
     * RecyclerView Item长按事件
     */
    void onLongClick(View view, int position);
}
