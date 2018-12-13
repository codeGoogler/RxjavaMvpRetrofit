package com.yuer.study.module.examples.mvploadimage;

import android.util.Log;

import com.facebook.stetho.common.LogUtil;
import com.yuer.study.ErrorAction;
import com.yuer.study.api.IMobileNewsApi;
import com.yuer.study.bean.news.NewsCommentBean;
import com.yuer.study.util.RetrofitFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 类功能描述：</br>
 *
 * @author 于亚豪
 * @version 1.0 </p> 修改时间：2018/11/23</br> 修改备注：</br>
 */
public  class UserModelImpl implements IUserModel{


    @Override
    public void loadUser(UserLoadListenner listener) {
        try {
            Map map= new HashMap<String,String>();
            map.put("key","00d91e8e0cca2b76f515926a36db68f5");
            map.put("passwd","123654");
            map.put("phone","13594347817");
           RetrofitFactory.getRetrofit(RetrofitFactory.RetrofitHostType.Study_Login).create(LoginAPi.class)
//                    .loginPost(map)
                    .loginPost2( )
//                    .loginPost2("00d91e8e0cca2b76f515926a36db68f5", "13594347817", "123654")
                    .subscribeOn(Schedulers.io())
                    .map(loginBean -> {
                        LogUtil.e("loginBean",loginBean.toString());
                       for (int i = 0; i < 100000; i++) {
                            LogUtil.e("count","this is count ："+i);
                        }
                            return loginBean;
                    })
//                    .map(new Function<LoginBean, Object>() {
//                        @Override
//                        public Object apply(LoginBean loginBean) throws Exception {
//                            LogUtil.e("loginBean",loginBean);
//                            return loginBean;
//                        }
//                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Object>() {

                        @Override
                        public void accept(Object o) throws Exception {
                            LoginBean loginBean = (LoginBean) o;
                            if (loginBean != null) {
                                listener.complete(loginBean);
                            }
                            LogUtil.e("Object", loginBean.toString());
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }

//                .as(view.bindAutoDispose())
//                .subscribe(list -> {
//                    if (null != list && list.size() > 0) {
//                        doSetAdapter(list);
//                    } else {
//                        doShowNoMore();
//                    }
//                }, throwable -> {
//                    doShowNetError();
//                    ErrorAction.print(throwable);
//                });
    }
}
