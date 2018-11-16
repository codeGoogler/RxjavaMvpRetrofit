package com.yuer.study.module.photo.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yuer.study.InitApp;
import com.yuer.study.R;
import com.yuer.study.bean.photo.PhotoArticleBean;
import com.yuer.study.module.base.BaseActivity;
import com.r0adkll.slidr.model.SlidrInterface;

/**
 * Created by Meiji on 2017/3/1.
 */

public class PhotoContentActivity extends BaseActivity {

    private static final String TAG = "PhotoContentActivity";

    public static void launch(PhotoArticleBean.DataBean bean) {
        InitApp.AppContext.startActivity(new Intent(InitApp.AppContext, PhotoContentActivity.class)
                .putExtra(TAG, bean)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, PhotoContentFragment.newInstance(getIntent().getParcelableExtra(TAG)))
                .commit();
    }

    public SlidrInterface getSlidrInterface() {
        return slidrInterface;
    }
}
