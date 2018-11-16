package com.yuer.study.module.media.home.tab;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.yuer.study.Register;
import com.yuer.study.bean.LoadingBean;
import com.yuer.study.bean.media.MediaProfileBean;
import com.yuer.study.module.base.BaseListFragment;
import com.yuer.study.util.DiffCallback;
import com.yuer.study.util.OnLoadMoreListener;

import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

import static com.yuer.study.module.media.home.tab.MediaTabPresenter.TYPE_ARTICLE;

/**
 * Created by Meiji on 2017/6/29.
 */

public class MediaArticleFragment extends BaseListFragment<IMediaProfile.Presenter> implements IMediaProfile.View, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "MediaArticleFragment";
    private MediaProfileBean.DataBean dataBean = null;

    public static MediaArticleFragment newInstance(Parcelable parcelable) {
        Bundle args = new Bundle();
        args.putParcelable(TAG, parcelable);
        MediaArticleFragment fragment = new MediaArticleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(IMediaProfile.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new MediaTabPresenter(this);
        }
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        adapter = new MultiTypeAdapter(oldItems);
        Register.registerMediaArticleItem(adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (canLoadMore) {
                    canLoadMore = false;
                    presenter.doLoadMoreData(TYPE_ARTICLE);
                }
            }
        });
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        this.dataBean = bundle.getParcelable(TAG);
        if (null == dataBean) {
            onShowNetError();
        }
    }

    @Override
    public void onSetAdapter(List<?> list) {
        Items newItems = new Items();
        newItems.add(dataBean);
        newItems.addAll(list);
        newItems.add(new LoadingBean());
        DiffCallback.create(oldItems, newItems, adapter);
        oldItems.clear();
        oldItems.addAll(newItems);
        canLoadMore = true;
        recyclerView.stopScroll();
    }

    @Override
    public void fetchData() {
        onLoadData();
    }

    @Override
    public void onLoadData() {
        presenter.doLoadArticle(dataBean.getMedia_id());
    }

    @Override
    public void onRefresh() {
        onShowLoading();
        presenter.doRefresh(TYPE_ARTICLE);
    }
}
