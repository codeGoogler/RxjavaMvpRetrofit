package com.yuer.study.module.video.article;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yuer.study.R;
import com.yuer.study.Register;
import com.yuer.study.bean.LoadingBean;
import com.yuer.study.module.base.BaseListFragment;
import com.yuer.study.module.news.article.INewsArticle;
import com.yuer.study.module.news.article.NewsArticlePresenter;
import com.yuer.study.util.DiffCallback;
import com.yuer.study.util.OnLoadMoreListener;
import com.yuer.study.widget.helper.MyJZVideoPlayerStandard;

import java.util.List;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerManager;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Meiji on 2017/3/29.
 */

public class VideoArticleView extends BaseListFragment<IVideoArticle.Presenter> implements IVideoArticle.View, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "VideoArticleView";
    private String categoryId;

    public static VideoArticleView newInstance(String categoryId) {
        Bundle bundle = new Bundle();
        bundle.putString(TAG, categoryId);
        VideoArticleView videoArticleView = new VideoArticleView();
        videoArticleView.setArguments(bundle);
        return videoArticleView;
    }

    @Override
    protected void initData() {
        categoryId = getArguments().getString(TAG);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        adapter = new MultiTypeAdapter(oldItems);
        Register.registerVideoArticleItem(adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (canLoadMore) {
                    canLoadMore = false;
                    presenter.doLoadMoreData();
                }
            }
        });
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                MyJZVideoPlayerStandard jzvd = view.findViewById(R.id.jc_video_item_video);
                if (jzvd != null && jzvd.isSaveFromParentEnabled()) {
                    JZVideoPlayer currentJzvd = JZVideoPlayerManager.getCurrentJzvd();
                    if (currentJzvd != null && currentJzvd.currentScreen != currentJzvd.SCREEN_WINDOW_FULLSCREEN) {
                        currentJzvd.releaseAllVideos();
                    }
                }
            }
        });

    }

    @Override
    public void fetchData() {
        super.fetchData();
        onLoadData();
    }

    @Override
    public void onLoadData() {
        onShowLoading();
        presenter.doLoadData(categoryId);
    }

    @Override
    public void onSetAdapter(final List<?> list) {
        Items newItems = new Items(list);
        newItems.add(new LoadingBean());
        DiffCallback.create(oldItems, newItems, adapter);
        oldItems.clear();
        oldItems.addAll(newItems);
        canLoadMore = true;
        recyclerView.stopScroll();
    }

    /**
     * API 跟新闻的一样 所以采用新闻的 presenter
     *
     * @param presenter
     */
//    @Override
//    public void setPresenter(INewsArticle.Presenter presenter) {
//        if (null == presenter) {
////            this.presenter = new NewsArticlePresenter(this);
//
//        }
//    }

    @Override
    public void setPresenter(IVideoArticle.Presenter presenter) {
        this.presenter = new VideoArticlePresenter(this);
    }
}
