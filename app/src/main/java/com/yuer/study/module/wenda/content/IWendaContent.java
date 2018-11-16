package com.yuer.study.module.wenda.content;

import com.yuer.study.bean.wenda.WendaContentBean;
import com.yuer.study.module.base.IBaseListView;
import com.yuer.study.module.base.IBasePresenter;

import java.util.List;

/**
 * Created by Meiji on 2017/5/22.
 */

public interface IWendaContent {

    interface View extends IBaseListView<Presenter> {

        /**
         * 请求数据
         */
        void onLoadData();

        /**
         * 刷新
         */
        void onRefresh();

        /**
         * 设置顶部信息
         */
        void onSetHeader(WendaContentBean.QuestionBean questionBean);
    }

    interface Presenter extends IBasePresenter {

        /**
         * 设置顶部信息
         */
        void doSetHeader(WendaContentBean.QuestionBean questionBean);

        /**
         * 请求数据
         */
        void doLoadData(String qid);

        /**
         * 再起请求数据
         */
        void doLoadMoreData();

        /**
         * 设置适配器
         */
        void doSetAdapter(List<WendaContentBean.AnsListBean> list);

        /**
         * 加载完毕
         */
        void doShowNoMore();
    }
}
