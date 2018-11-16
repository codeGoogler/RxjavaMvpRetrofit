package com.yuer.study.module.media.home.tab;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.yuer.study.Register;
import com.yuer.study.bean.LoadingBean;
import com.yuer.study.module.base.BaseListFragment;
import com.yuer.study.util.DiffCallback;
import com.yuer.study.util.OnLoadMoreListener;

import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

import static com.yuer.study.module.media.home.tab.MediaTabPresenter.TYPE_WENDA;

/**
 * Created by Meiji on 2017/6/29.
 */

public class MediaWendaFragment extends BaseListFragment<IMediaProfile.Presenter> implements IMediaProfile.View {

    private static final String TAG = "MediaWendaFragment";
    private String mediaId;

    public static MediaWendaFragment newInstance(String mediaId) {
        Bundle args = new Bundle();
        args.putString(TAG, mediaId);
        MediaWendaFragment fragment = new MediaWendaFragment();
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
        Register.registerMediaWendaItem(adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (canLoadMore) {
                    canLoadMore = false;
                    presenter.doLoadMoreData(TYPE_WENDA);
                }
            }
        });
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        mediaId = bundle.getString(TAG);
        if (TextUtils.isEmpty(mediaId)) {
            onShowNetError();
        }
    }

    @Override
    public void onLoadData() {
        onShowLoading();
        presenter.doLoadWenda(mediaId);
    }

    @Override
    public void onRefresh() {
        presenter.doRefresh(TYPE_WENDA);
    }

    @Override
    public void onSetAdapter(List<?> list) {
        Items newItems = new Items(list);
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
}
