package com.yuer.study;

import android.support.annotation.NonNull;

import com.yuer.study.bean.LoadingBean;
import com.yuer.study.bean.LoadingEndBean;
import com.yuer.study.bean.media.MediaChannelBean;
import com.yuer.study.bean.media.MediaProfileBean;
import com.yuer.study.bean.media.MediaWendaBean;
import com.yuer.study.bean.media.MultiMediaArticleBean;
import com.yuer.study.bean.news.MultiNewsArticleDataBean;
import com.yuer.study.bean.news.NewsCommentBean;
import com.yuer.study.bean.photo.PhotoArticleBean;
import com.yuer.study.bean.wenda.WendaArticleDataBean;
import com.yuer.study.bean.wenda.WendaContentBean;
import com.yuer.study.binder.LoadingEndViewBinder;
import com.yuer.study.binder.LoadingViewBinder;
import com.yuer.study.binder.media.MediaArticleHeaderViewBinder;
import com.yuer.study.binder.media.MediaArticleImgViewBinder;
import com.yuer.study.binder.media.MediaArticleTextViewBinder;
import com.yuer.study.binder.media.MediaArticleVideoViewBinder;
import com.yuer.study.binder.media.MediaChannelViewBinder;
import com.yuer.study.binder.media.MediaWendaViewBinder;
import com.yuer.study.binder.news.NewsArticleImgViewBinder;
import com.yuer.study.binder.news.NewsArticleTextViewBinder;
import com.yuer.study.binder.news.NewsArticleVideoViewBinder;
import com.yuer.study.binder.news.NewsCommentViewBinder;
import com.yuer.study.binder.photo.PhotoArticleViewBinder;
import com.yuer.study.binder.search.SearchArticleVideoViewBinder;
import com.yuer.study.binder.video.VideoContentHeaderViewBinder;
import com.yuer.study.binder.wenda.WendaArticleOneImgViewBinder;
import com.yuer.study.binder.wenda.WendaArticleTextViewBinder;
import com.yuer.study.binder.wenda.WendaArticleThreeImgViewBinder;
import com.yuer.study.binder.wenda.WendaContentHeaderViewBinder;
import com.yuer.study.binder.wenda.WendaContentViewBinder;
import com.yuer.study.interfaces.IOnItemLongClickListener;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Meiji on 2017/6/9.
 */

public class Register {

    public static void registerNewsArticleItem(@NonNull MultiTypeAdapter adapter) {
        // 一个类型对应多个 ItemViewBinder
        adapter.register(MultiNewsArticleDataBean.class)
                .to(new NewsArticleImgViewBinder(),
                        new NewsArticleVideoViewBinder(),
                        new NewsArticleTextViewBinder())
                .withClassLinker((position, item) -> {
                    if (item.isHas_video()) {
                        return NewsArticleVideoViewBinder.class;
                    }
                    if (null != item.getImage_list() && item.getImage_list().size() > 0) {
                        return NewsArticleImgViewBinder.class;
                    }
                    return NewsArticleTextViewBinder.class;
                });
        adapter.register(LoadingBean.class, new LoadingViewBinder());
        adapter.register(LoadingEndBean.class, new LoadingEndViewBinder());
    }

    public static void registerNewsCommentItem(@NonNull MultiTypeAdapter adapter) {
        adapter.register(NewsCommentBean.DataBean.CommentBean.class, new NewsCommentViewBinder());
        adapter.register(LoadingBean.class, new LoadingViewBinder());
        adapter.register(LoadingEndBean.class, new LoadingEndViewBinder());
    }

    public static void registerVideoContentItem(@NonNull MultiTypeAdapter adapter) {
        adapter.register(MultiNewsArticleDataBean.class, new VideoContentHeaderViewBinder());
        adapter.register(NewsCommentBean.DataBean.CommentBean.class, new NewsCommentViewBinder());
        adapter.register(LoadingBean.class, new LoadingViewBinder());
        adapter.register(LoadingEndBean.class, new LoadingEndViewBinder());
    }

    public static void registerVideoArticleItem(@NonNull MultiTypeAdapter adapter) {
        adapter.register(MultiNewsArticleDataBean.class, new NewsArticleVideoViewBinder());
        adapter.register(LoadingBean.class, new LoadingViewBinder());
        adapter.register(LoadingEndBean.class, new LoadingEndViewBinder());
    }

    public static void registerPhotoArticleItem(@NonNull MultiTypeAdapter adapter) {
        adapter.register(PhotoArticleBean.DataBean.class, new PhotoArticleViewBinder());
        adapter.register(LoadingBean.class, new LoadingViewBinder());
        adapter.register(LoadingEndBean.class, new LoadingEndViewBinder());
    }

    public static void registerWendaArticleItem(@NonNull MultiTypeAdapter adapter) {
        // 一个类型对应多个 ItemViewBinder
        adapter.register(WendaArticleDataBean.class)
                .to(new WendaArticleTextViewBinder(),
                        new WendaArticleOneImgViewBinder(),
                        new WendaArticleThreeImgViewBinder())
                .withClassLinker((position, item) -> {
                    if (null != item.getExtraBean().getWenda_image() &&
                            null != item.getExtraBean().getWenda_image().getThree_image_list() &&
                            item.getExtraBean().getWenda_image().getThree_image_list().size() > 0) {
                        return WendaArticleThreeImgViewBinder.class;
                    }
                    if (null != item.getExtraBean().getWenda_image() &&
                            null != item.getExtraBean().getWenda_image().getLarge_image_list() &&
                            item.getExtraBean().getWenda_image().getLarge_image_list().size() > 0) {
                        return WendaArticleOneImgViewBinder.class;
                    }
                    return WendaArticleTextViewBinder.class;
                });
        adapter.register(LoadingBean.class, new LoadingViewBinder());
        adapter.register(LoadingEndBean.class, new LoadingEndViewBinder());
    }

    public static void registerWendaContentItem(@NonNull MultiTypeAdapter adapter) {
        adapter.register(WendaContentBean.QuestionBean.class, new WendaContentHeaderViewBinder());
        adapter.register(WendaContentBean.AnsListBean.class, new WendaContentViewBinder());
        adapter.register(LoadingBean.class, new LoadingViewBinder());
        adapter.register(LoadingEndBean.class, new LoadingEndViewBinder());
    }

    public static void registerMediaChannelItem(@NonNull MultiTypeAdapter adapter, @NonNull IOnItemLongClickListener listener) {
        adapter.register(MediaChannelBean.class, new MediaChannelViewBinder(listener));
    }

    public static void registerSearchItem(@NonNull MultiTypeAdapter adapter) {
        adapter.register(MultiNewsArticleDataBean.class)
                .to(new NewsArticleImgViewBinder(),
                        new SearchArticleVideoViewBinder(),
                        new NewsArticleTextViewBinder())
                .withClassLinker((position, item) -> {
                    if (item.isHas_video()) {
                        return SearchArticleVideoViewBinder.class;
                    }
                    if (null != item.getImage_list() && item.getImage_list().size() > 0) {
                        return NewsArticleImgViewBinder.class;
                    }
                    return NewsArticleTextViewBinder.class;
                });
        adapter.register(LoadingBean.class, new LoadingViewBinder());
        adapter.register(LoadingEndBean.class, new LoadingEndViewBinder());
    }

    public static void registerMediaArticleItem(@NonNull MultiTypeAdapter adapter) {
        adapter.register(MultiMediaArticleBean.DataBean.class)
                .to(new MediaArticleImgViewBinder(),
                        new MediaArticleVideoViewBinder(),
                        new MediaArticleTextViewBinder())
                .withClassLinker((position, item) -> {
                    if (item.isHas_video()) {
                        return MediaArticleVideoViewBinder.class;
                    }
                    if (null != item.getImage_list() && item.getImage_list().size() > 0) {
                        return MediaArticleImgViewBinder.class;
                    }
                    return MediaArticleTextViewBinder.class;
                });
        adapter.register(MediaProfileBean.DataBean.class, new MediaArticleHeaderViewBinder());
        adapter.register(LoadingBean.class, new LoadingViewBinder());
        adapter.register(LoadingEndBean.class, new LoadingEndViewBinder());
    }

    public static void registerMediaWendaItem(@NonNull MultiTypeAdapter adapter) {
        adapter.register(MediaWendaBean.AnswerQuestionBean.class, new MediaWendaViewBinder());
        adapter.register(LoadingBean.class, new LoadingViewBinder());
        adapter.register(LoadingEndBean.class, new LoadingEndViewBinder());
    }
}
