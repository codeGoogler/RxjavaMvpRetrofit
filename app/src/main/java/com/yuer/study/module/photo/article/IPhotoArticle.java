package com.yuer.study.module.photo.article;

import com.yuer.study.bean.photo.PhotoArticleBean;
import com.yuer.study.module.base.IBaseListView;
import com.yuer.study.module.base.IBasePresenter;

import java.util.List;

interface IPhotoArticle {

    interface View extends IBaseListView<Presenter> {

        /**
         * 请求数据
         */
        void onLoadData();
    }

    interface Presenter extends IBasePresenter {

        /**
         * 请求数据
         */
        void doLoadData(String... category);

        /**
         * 再起请求数据
         */
        void doLoadMoreData();

        /**
         * 设置适配器
         */
        void doSetAdapter(List<PhotoArticleBean.DataBean> dataBeen);

        void doShowNoMore();
    }
}
