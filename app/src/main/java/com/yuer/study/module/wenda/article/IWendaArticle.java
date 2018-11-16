package com.yuer.study.module.wenda.article;

import com.yuer.study.bean.wenda.WendaArticleDataBean;
import com.yuer.study.module.base.IBaseListView;
import com.yuer.study.module.base.IBasePresenter;

import java.util.List;

/**
 * Created by Meiji on 2017/5/20.
 */

public interface IWendaArticle {

    interface View extends IBaseListView<Presenter> {

        /**
         * 请求数据
         */
        void onLoadData();

        /**
         * 刷新
         */
        void onRefresh();
    }

    interface Presenter extends IBasePresenter {

        /**
         * 请求数据
         */
        void doLoadData();

        /**
         * 再起请求数据
         */
        void doLoadMoreData();

        /**
         * 设置适配器
         */
        void doSetAdapter(List<WendaArticleDataBean> list);
    }
}
