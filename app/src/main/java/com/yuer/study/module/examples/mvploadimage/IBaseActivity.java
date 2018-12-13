package com.yuer.study.module.examples.mvploadimage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

/**
 * 类功能描述：</br>
 *
 * @author 于亚豪
 * @version 1.0 </p> 修改时间：2018/11/23</br> 修改备注：</br>
 * https://blog.csdn.net/u012124438/article/details/51627220
 */
public abstract class IBaseActivity<V , T extends BasePresenter<V>> extends FragmentActivity {

    protected T mPresenter;//Presenter对象

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();//创建Presenter
        mPresenter.attachView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
    protected abstract T createPresenter();
}
