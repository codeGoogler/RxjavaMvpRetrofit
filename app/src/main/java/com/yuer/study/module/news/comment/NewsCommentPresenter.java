package com.yuer.study.module.news.comment;

import com.yuer.study.ErrorAction;
import com.yuer.study.api.IMobileNewsApi;
import com.yuer.study.bean.news.NewsCommentBean;
import com.yuer.study.util.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2016/12/20.
 */

public class NewsCommentPresenter implements INewsComment.Presenter {

    private static final String TAG = "NewsCommentPresenter";
    private INewsComment.View view;
    private String groupId;
    private String itemId;
    private int offset = 0;
    private List<NewsCommentBean.DataBean.CommentBean> commentsBeanList = new ArrayList<>();

    public NewsCommentPresenter(INewsComment.View view) {
        this.view = view;
    }

    @Override
    public void doLoadData(String... groupId_ItemId) {

        try {
            if (null == this.groupId) {
                this.groupId = groupId_ItemId[0];
            }
            if (null == this.itemId) {
                this.itemId = groupId_ItemId[1];
            }
        } catch (Exception e) {
            ErrorAction.print(e);
        }

        RetrofitFactory.getRetrofit().create(IMobileNewsApi.class)
                .getNewsComment(groupId, offset)
                .subscribeOn(Schedulers.io())
                .map(newsCommentBean -> {
                    List<NewsCommentBean.DataBean.CommentBean> data = new ArrayList<>();
                    for (NewsCommentBean.DataBean bean : newsCommentBean.getData()) {
                        data.add(bean.getComment());
                    }
                    return data;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .as(view.bindAutoDispose())
                .subscribe(list -> {
                    if (null != list && list.size() > 0) {
                        doSetAdapter(list);
                    } else {
                        doShowNoMore();
                    }
                }, throwable -> {
                    doShowNetError();
                    ErrorAction.print(throwable);
                });
    }

    @Override
    public void doLoadMoreData() {
        offset += 20;
        doLoadData();
    }

    @Override
    public void doSetAdapter(List<NewsCommentBean.DataBean.CommentBean> list) {
        commentsBeanList.addAll(list);
        view.onSetAdapter(commentsBeanList);
        view.onHideLoading();
    }

    @Override
    public void doRefresh() {
        if (commentsBeanList.size() != 0) {
            commentsBeanList.clear();
            offset = 0;
        }
        doLoadData();
    }

    @Override
    public void doShowNetError() {
        view.onHideLoading();
        view.onShowNetError();
    }

    @Override
    public void doShowNoMore() {
        view.onHideLoading();
        view.onShowNoMore();
    }
}
