package com.yuer.study.module.media.home.tab;

import com.yuer.study.bean.media.MediaWendaBean;
import com.yuer.study.bean.media.MultiMediaArticleBean;
import com.yuer.study.module.base.IBaseListView;
import com.yuer.study.module.base.IBasePresenter;

import java.util.List;

/**
 * Created by Meiji on 2017/7/1.
 */

public interface IMediaProfile {

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
        void doLoadArticle(String... mediaId);

        void doLoadVideo(String... mediaId);

        void doLoadWenda(String... mediaId);

        /**
         * 再起请求数据
         */
        void doLoadMoreData(int type);

        /**
         * 设置适配器
         */
        void doSetAdapter(List<MultiMediaArticleBean.DataBean> list);

        void doSetWendaAdapter(List<MediaWendaBean.AnswerQuestionBean> list);

        void doRefresh(int type);

        void doShowNoMore();
    }
}
