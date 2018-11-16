package com.yuer.study.binder.news;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jakewharton.rxbinding2.view.RxView;
import com.yuer.study.ErrorAction;
import com.yuer.study.IntentAction;
import com.yuer.study.R;
import com.yuer.study.bean.news.MultiNewsArticleDataBean;
import com.yuer.study.module.video.content.VideoContentActivity;
import com.yuer.study.util.GlideApp;
import com.yuer.study.util.ImageLoader;
import com.yuer.study.util.SettingUtil;
import com.yuer.study.util.TimeUtil;
import com.yuer.study.widget.CircleImageView;
import com.yuer.study.widget.helper.MyJZVideoPlayerStandard;

import java.util.concurrent.TimeUnit;

import cn.jzvd.JZUserAction;
import cn.jzvd.JZUserActionStandard;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import me.drakeet.multitype.ItemViewBinder;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by Meiji on 2017/6/8.
 */

public class NewsArticleVideoViewBinder extends ItemViewBinder<MultiNewsArticleDataBean, NewsArticleVideoViewBinder.ViewHolder> {

    private static final String TAG = "NewsArticleHasVideoView";

    @NonNull
    @Override
    protected NewsArticleVideoViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_news_article_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final NewsArticleVideoViewBinder.ViewHolder holder, @NonNull final MultiNewsArticleDataBean item) {

        final Context context = holder.itemView.getContext();

        try {
//            if (null != item.getVideo_detail_info()) {
//                if (null != item.getVideo_detail_info().getDetail_video_large_image()) {
//                    String image = item.getVideo_detail_info().getDetail_video_large_image().getUrl();
//                    if (!TextUtils.isEmpty(image)) {
//                        ImageLoader.loadCenterCrop(context, image, holder.iv_video_image, R.color.viewBackground, R.mipmap.error_image);
//                    }
//                }
//            } else {
//                holder.iv_video_image.setImageResource(R.mipmap.error_image);
//            }

            if (null != item.getUser_info()) {
                String avatar_url = item.getUser_info().getAvatar_url();
                if (!TextUtils.isEmpty(avatar_url)) {
                    ImageLoader.loadCenterCrop(context, avatar_url, holder.iv_media, R.color.viewBackground);
                }
            }

            String tv_title = item.getTitle();
            holder.tv_title.setTextSize(SettingUtil.getInstance().getTextSize());
            String tv_source = item.getSource();
            String tv_comment_count = item.getComment_count() + "评论";
            String tv_datetime = item.getBehot_time() + "";
            if (!TextUtils.isEmpty(tv_datetime)) {
                tv_datetime = TimeUtil.getTimeStampAgo(tv_datetime);
            }
            int video_duration = item.getVideo_duration();
            String min = String.valueOf(video_duration / 60);
            String second = String.valueOf(video_duration % 10);
            if (Integer.parseInt(second) < 10) {
                second = "0" + second;
            }
            String tv_video_time = min + ":" + second;

            holder.tv_title.setText(tv_title);
            holder.tv_extra.setText(tv_source + " - " + tv_comment_count + " - " + tv_datetime);
//            holder.tv_video_time.setText(tv_video_time);
            holder.iv_dots.setOnClickListener(view -> {
                PopupMenu popupMenu = new PopupMenu(context,
                        holder.iv_dots, Gravity.END, 0, R.style.MyPopupMenu);
                popupMenu.inflate(R.menu.menu_share);
                popupMenu.setOnMenuItemClickListener(menu -> {
                    int itemId = menu.getItemId();
                    if (itemId == R.id.action_share) {
                        IntentAction.send(context, item.getTitle() + "\n" + item.getShare_url());
                    }
                    return false;
                });

               /* Glide.with(context).load("").placeholder(R.mipmap.error_image)
                        .error(R.mipmap.error_image).into(holder.jc_video_item_video );*/

                Glide.with(context)
                        .load("")
                        .transition(withCrossFade())
                        .apply(new RequestOptions().centerCrop().error(R.mipmap.error_image))
                        .into(holder.jc_video_item_video.thumbImageView);
                holder.jc_video_item_video.setUp("http://v9-tt.ixigua.com/67fd14bbd30703f73c8a2b1fb1a77e00/5bebe8bf/video/m/220a8ce6eac09144f6d97ebcbbfe8287e38115f26c600007c8487b3c7a3/?rc=anc5cXk7O25zaTMzZTczM0ApQHRAbzM8Njc0MzMzMzM0NDQ0NDVvQGgzdSlAZjN1KWRzcmd5a3VyZ3lybHh3ZjUzQDAwNjZpMmxuaV8tLS0tL3NzLW8jbyM1Ly8uMzUtLjU0MzEuNS06I28jOmEtcSM6YHZpXGJmK2BeYmYrXnFsOiMuL14%3D", JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
                if (SettingUtil.getInstance().getIsVideoAutoPlay()) {
                    holder.jc_video_item_video.startButton.performClick();
                }
                // 设置监听事件 判断是否进入全屏
                JZVideoPlayer.setJzUserAction((type, url, screen, objects) -> {
                    if (type == JZUserActionStandard.ON_CLICK_START_THUMB ||
                            type == JZUserAction.ON_CLICK_START_ICON ||
                            type == JZUserAction.ON_CLICK_RESUME ||
                            type == JZUserAction.ON_CLICK_START_AUTO_COMPLETE) {
                    }

                    if (type == JZUserAction.ON_CLICK_PAUSE || type == JZUserAction.ON_AUTO_COMPLETE) {
                    }

                    if (type == JZUserAction.ON_ENTER_FULLSCREEN) {
//                        currentAction = JZUserAction.ON_ENTER_FULLSCREEN;
//
//                        View decorView = getWindow().getDecorView();
                        int uiOptions = 0;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                        } else {
                            uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                        }
//                        decorView.setSystemUiVisibility(uiOptions);

                        /*if (slidrInterface != null) {
                            slidrInterface.lock();
                        }*/
                    }

                    if (type == JZUserAction.ON_QUIT_FULLSCREEN) {
//                        currentAction = JZUserAction.ON_QUIT_FULLSCREEN;
//
//                        View decorView = getWindow().getDecorView();
//                        decorView.setSystemUiVisibility(0);
//
//                        if (slidrInterface != null) {
//                            slidrInterface.unlock();
//                        }
                    }
                });
//                ImageLoader.loadCenterCrop(this, image, jcVideo.thumbImageView, R.color.viewBackground, R.mipmap.error_image);
                popupMenu.show();
            });

            RxView.clicks(holder.itemView)
                    .throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe(o -> VideoContentActivity.launch(item));
        } catch (Exception e) {
            ErrorAction.print(e);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView iv_media;
        private TextView tv_extra;
        private TextView tv_title;
//        private ImageView iv_video_image;
//        private TextView tv_video_time;
        private ImageView iv_dots;
        private MyJZVideoPlayerStandard jc_video_item_video;

        ViewHolder(View itemView) {
            super(itemView);
            this.iv_media = itemView.findViewById(R.id.iv_media);
            this.tv_extra = itemView.findViewById(R.id.tv_extra);
            this.tv_title = itemView.findViewById(R.id.tv_title);
//            this.iv_video_image = itemView.findViewById(R.id.iv_video_image);
//            this.tv_video_time = itemView.findViewById(R.id.tv_video_time);
            this.iv_dots = itemView.findViewById(R.id.iv_dots);
            this.jc_video_item_video = itemView.findViewById(R.id.jc_video_item_video);
        }
    }
}
