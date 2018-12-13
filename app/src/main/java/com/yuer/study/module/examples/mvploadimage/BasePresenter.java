package com.yuer.study.module.examples.mvploadimage;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * 类功能描述：</br>
 *
 * @author 于亚豪
 * @version 1.0 </p> 修改时间：2018/11/23</br> 修改备注：</br>
 * https://blog.csdn.net/u012124438/article/details/51627220
 */
public abstract class BasePresenter<T>  {

    protected Reference<T> mViewRef;//View接口类型弱引用

    public void attachView(T view) {
        mViewRef = new WeakReference<T>(view); //建立关联
    }

    protected T getView() {
        return mViewRef.get();//获取View
    }

    public boolean isViewAttached() {//判断是否与View建立了关联
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {//解除关联
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
