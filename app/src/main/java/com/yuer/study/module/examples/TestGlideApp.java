package com.yuer.study.module.examples;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.yuer.study.InitApp;
import com.yuer.study.R;
import com.yuer.study.module.base.BaseActivity;
import com.yuer.study.util.DownloadUtil;
import com.yuer.study.widget.ProgressBarVShowDialog;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 类功能描述：</br>
 *
 * @author 于亚豪
 * @version 1.0 </p> 修改时间：2018/11/16</br> 修改备注：</br>
 */
public class TestGlideApp extends BaseActivity {

    ImageView image1;
    ImageView image2;
    ImageView image3;
    Button btn_cache_imageView;
    Button btn_cache_imageView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        btn_cache_imageView = findViewById(R.id.btn_cache_imageView);
        btn_cache_imageView2 = findViewById(R.id.btn_cache_imageView2);


        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn_cache_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNormble();
            }
        });
        btn_cache_imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                image1.setImageBitmap(DownloadUtil.getCacheImage("https://csdnimg.cn/pubfooter/images/app.png",TestGlideApp.this));
//                image2.setImageBitmap(DownloadUtil.getCacheImage("https://csdnimg.cn/pubfooter/images/app.png",TestGlideApp.this));
//                image3.setImageBitmap(DownloadUtil.getCacheImage("https://csdnimg.cn/pubfooter/images/app.png",TestGlideApp.this));
//                AsyncTaskTest asyncTaskTest = new AsyncTaskTest();
//                asyncTaskTest.execute("https://csdnimg.cn/pubfooter/images/app.png");
                //getGlideCacheBitmap("https://csdnimg.cn/pubfooter/images/app.png1",R.mipmap.ic_channel_edit);
                try{
                    if(progressBarVShowDialog == null){
                        progressBarVShowDialog = new ProgressBarVShowDialog(TestGlideApp.this).setLoadingTip("正在获取图片...");
                    }
                    DownloadUtil.getGlideCacheBitmap(TestGlideApp.this, "https://csdnimg.cn/pubfooter/images/app.png", R.mipmap.ic_channel_edit, progressBarVShowDialog,new DownloadUtil.LoadImageCacheGlideListaner() {
                        @Override
                        public void loadImageCacheGlide(Bitmap bitmap) {
                            image3.setImageBitmap(bitmap);
                        }

                        @Override
                        public void loadFaild(Bitmap bitmap) {
                            image3.setImageBitmap(bitmap);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
    /* 解决部分图片加载不出来的问题
    *http://abv.cn/music/光辉岁月.mp3
           * @param context
    * @param imageView
    * @param imgUrl
    * @param sourseId
    */
    public static void loadNetImg2(Context context, final ImageView imageView, String imgUrl, final int sourseId){
        RequestOptions options = new RequestOptions();
        options.error(sourseId);
        options.skipMemoryCache(true);
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        RequestBuilder<Drawable> requestBuilder =
                Glide.with(context)
                        .load(imgUrl)
                        .apply(options).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        imageView.setBackgroundResource(sourseId);
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                });
    }
    /**
     * 解决部分图片加载不出来的问题
     * 不加载到缓存
     * @param context
     * @param imageView
     * @param imgUrl
     * @param sourseId
     */
    public static void loadNetImg3(Context context, final ImageView imageView, String imgUrl, final int sourseId){
        RequestOptions options = new RequestOptions();
        options.error(sourseId);
        options.placeholder(sourseId);
        options.skipMemoryCache(false);
        options.diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(context)
                .load(imgUrl)
                .apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.d("yyh", "onException: " + e.toString()+"  model:"+model+" isFirstResource: "+isFirstResource);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.e("yyh", "isFirstResource:"+isFirstResource+"  model:"+model+" isFirstResource: "+isFirstResource);
                        Log.e("yyh", "dataSource:"+dataSource+"  model:"+model+" isFirstResource: "+isFirstResource);

                        return false;
                    }
                }).into(imageView);
    }
    private void loadNormble() {
//        loadNetImg3(TestGlideApp.this,image1,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496754078616&di=cc68338a66a36de619fa11d0c1b2e6f3&imgtype=0&src=http%3A%2F%2Fapp.576tv.com%2FUploads%2Foltz%2F201609%2F25%2F1474813626468299.gif", android.R.mipmap.sym_def_app_icon);
//        loadNetImg3(TestGlideApp.this,image2,"https://raw.githubusercontent.com/sfsheng0322/GlideImageView/master/screenshot/cat.jpg", android.R.mipmap.sym_def_app_icon);
//        loadNetImg3(TestGlideApp.this,image3,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496754078616&di=cc68338a66a36de619fa11d0c1b2e6f3&imgtype=0&src=http%3A%2F%2Fapp.576tv.com%2FUploads%2Foltz%2F201609%2F25%2F1474813626468299.gif", android.R.mipmap.sym_def_app_icon);
        loadNetImg3(TestGlideApp.this,image1,"https://csdnimg.cn/pubfooter/images/app.png", android.R.mipmap.sym_def_app_icon);
        loadNetImg3(TestGlideApp.this,image2,"https://csdnimg.cn/pubfooter/images/app.png", android.R.mipmap.sym_def_app_icon);
        loadNetImg3(TestGlideApp.this,image3,"https://csdnimg.cn/pubfooter/images/app.png", android.R.mipmap.sym_def_app_icon);


    }

    public void  ss(){
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.schedule(new Runnable() {

            @Override
            public void run() {
                System.out.println("delay 3 seconds");
                Log.e("yyh","delay 3 seconds");
            }
        }, 3, TimeUnit.SECONDS);

    }

    private class AsyncTaskTest extends AsyncTask<String ,Integer, Bitmap>{
        //        @Override
//        protected Bitmap doInBackground(String... strings) {
//            // 获取 bitmap
//            Bitmap bitmap = null;
//            try {
//                bitmap = Glide.with(TestGlideApp.this).asBitmap().load(strings[0])
//                        .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                        .get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//            if (bitmap== null){
//                bitmap = BitmapFactory.decodeResource(TestGlideApp.this.getResources(),android.R.mipmap.sym_def_app_icon);
//            }
//            return bitmap;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            super.onPostExecute(bitmap);
//
//            image1.setImageBitmap(bitmap);
//            image2.setImageBitmap(bitmap);
//            image3.setImageBitmap(bitmap);
//        }
        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            try {
                bitmap = Glide.with(TestGlideApp.this).asBitmap().load(strings[0])
                        .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if (bitmap== null){
                bitmap = BitmapFactory.decodeResource(TestGlideApp.this.getResources(),android.R.mipmap.sym_def_app_icon);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            try {

                File appDir = new File(Environment.getExternalStorageDirectory(), "yuyahao");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String fileName = System.currentTimeMillis() + ".jpg";
                File file = new File(appDir, fileName);
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();

                String path = file.getAbsolutePath();


                // 其次把文件插入到系统图库
                MediaStore.Images.Media.insertImage(InitApp.AppContext.getContentResolver(), file.getAbsolutePath(), fileName, null);
                // 最后通知图库更新
                TestGlideApp.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
                File file1 = new File(path);
                if (file1.exists()){
                    image1.setImageURI(Uri.fromFile(file1));
                }

                image2.setImageURI(Uri.parse("file://" + path));


                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                image3.setImageBitmap(bitmap);

                bos.flush();
                bos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    private BitmapFactory.Options getBitmapOption(int inSampleSize)

    {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

    private ProgressBarVShowDialog progressBarVShowDialog;

    public void getGlideCacheBitmap(String url,int defaultImg){
        new AsyncTask<String, Integer, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... strings) {
                Log.e("yuyahao"," : doInBackground");
                Bitmap bitmap = null;
                try {
                    bitmap = Glide.with(TestGlideApp.this).asBitmap().load(strings[0])
                            .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                if (bitmap== null){
                    bitmap = BitmapFactory.decodeResource(TestGlideApp.this.getResources(),defaultImg);
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                Log.e("yuyahao"," : onPostExecute");
                try{
                    File appDir = new File(Environment.getExternalStorageDirectory(), "yuyahao");
                    if (!appDir.exists()) {
                        appDir.mkdir();
                    }
                    String fileName = System.currentTimeMillis() + ".jpg";
                    File file = new File(appDir, fileName);
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();

                    String path = file.getAbsolutePath();


                    // 其次把文件插入到系统图库
                    MediaStore.Images.Media.insertImage(InitApp.AppContext.getContentResolver(), file.getAbsolutePath(), fileName, null);
                    // 最后通知图库更新
                    TestGlideApp.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
                   File file1 = new File(path);
                    if (file1.exists()){
                        image1.setImageURI(Uri.fromFile(file1));
                    }

                    image2.setImageURI(Uri.parse("file://" + path));


                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    image3.setImageBitmap(bitmap);

                    bos.flush();
                    bos.close();
                     cancel(true);
                     onCancelled();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.e("yuyahao"," : onPostExecute");
                if(progressBarVShowDialog == null){
                    progressBarVShowDialog = new ProgressBarVShowDialog(TestGlideApp.this).setLoadingTip("正在获取图片...");
                    progressBarVShowDialog.showDialog();
                }else{
                    progressBarVShowDialog.showDialog();
                }

            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                Log.e("yuyahao","onProgressUpdate: "+values[0]);
            }

            @Override
            protected void onCancelled(Bitmap bitmap) {
                super.onCancelled(bitmap);
                progressBarVShowDialog.disMymiss();
                Log.e("yuyahao"," : onCancelled");
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                progressBarVShowDialog.disMymiss();
                Log.e("yuyahao"," : onCancelled");
            }
        }.execute(url);
    }
}
