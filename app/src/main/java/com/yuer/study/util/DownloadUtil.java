package com.yuer.study.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.yuer.study.ErrorAction;
import com.yuer.study.InitApp;
import com.yuer.study.R;
import com.yuer.study.module.examples.TestGlideApp;
import com.yuer.study.widget.ProgressBarVShowDialog;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static android.R.attr.path;

/**
 * Created by Meiji on 2018/3/23.
 */

public class DownloadUtil {

    public static Boolean saveImage(String url, Context context) {
        boolean flag = false;
        try {
            // 获取 bitmap
            Bitmap bitmap = Glide.with(context).asBitmap().load(url)
                    .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            // http://stormzhang.com/android/2014/07/24/android-save-image-to-gallery/
            if (bitmap != null) {
                // 首先保存图片
                File appDir = new File(Environment.getExternalStorageDirectory(), "Toutiao");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String fileName = System.currentTimeMillis() + ".jpg";
                File file = new File(appDir, fileName);
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();

                // 其次把文件插入到系统图库
//                MediaStore.Images.Media.insertImage(InitApp.AppContext.getContentResolver(), file.getAbsolutePath(), fileName, null);
                // 最后通知图库更新
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));

                flag = true;
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            ErrorAction.print(e);
            return false;
        }
        return flag;
    }
    public static Bitmap getCacheImage(String url, Context context) {
        boolean flag = false;
        Bitmap bitmap = null;
        try {
            // 获取 bitmap
              bitmap = Glide.with(context).asBitmap().load(url)
                    .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            // http://stormzhang.com/android/2014/07/24/android-save-image-to-gallery/
            if (bitmap != null) {
                // 首先保存图片
                File appDir = new File(Environment.getExternalStorageDirectory(), "Toutiao");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String fileName = System.currentTimeMillis() + ".jpg";
                File file = new File(appDir, fileName);
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();

                // 其次把文件插入到系统图库
//                MediaStore.Images.Media.insertImage(InitApp.AppContext.getContentResolver(), file.getAbsolutePath(), fileName, null);
                // 最后通知图库更新
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));

                flag = true;
            }else{
                bitmap = BitmapFactory.decodeResource(context.getResources(),android.R.mipmap.sym_def_app_icon);
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            ErrorAction.print(e);
            bitmap = BitmapFactory.decodeResource(context.getResources(),android.R.mipmap.sym_def_app_icon);
        }
        return bitmap;
    }


    public interface LoadImageCacheGlideListaner {
        void loadImageCacheGlide(Bitmap bitmap);
        void loadFaild(Bitmap bitmap);
    }

    private static Activity  context;
    @SuppressLint("StaticFieldLeak")
    public  static  void getGlideCacheBitmap(Activity  activity, String url, int defaultImg,final ProgressBarVShowDialog progressBarVShowDialog , LoadImageCacheGlideListaner loadImageCacheGlide ){
    
        context = activity;
        AsyncTask<String, Integer, Bitmap> execute = new AsyncTask<String, Integer, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... strings) {
                Log.e("yuyahao", " : doInBackground");
                Bitmap bitmap = null;
                try {
                    bitmap = Glide.with(context).asBitmap().load(strings[0])
                            .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                if (bitmap == null) {
                    bitmap = BitmapFactory.decodeResource(context.getResources(), defaultImg);
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                Log.e("yuyahao", " : onPostExecute");
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
                    // MediaStore.Images.Media.insertImage(InitApp.AppContext.getContentResolver(), file.getAbsolutePath(), fileName, null);
                    // 最后通知图库更新
                    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
//                    File file1 = new File(path);
//                    if (file1.exists()){
//                        image1.setImageURI(Uri.fromFile(file1));
//                    }
//
//                    image2.setImageURI(Uri.parse("file://" + path));
//
//
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//                    image3.setImageBitmap(bitmap);
                    if (loadImageCacheGlide != null) {
                        loadImageCacheGlide.loadImageCacheGlide(bitmap);
                    }
                    bos.flush();
                    bos.close();
                    cancel(true);
                    onCancelled();
                    onCancelled(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    bitmap = BitmapFactory.decodeResource(context.getResources(), defaultImg);
                    if (loadImageCacheGlide != null) {
                        loadImageCacheGlide.loadFaild(bitmap);
                    }
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.e("yuyahao", " : onPostExecute");
                if (progressBarVShowDialog == null) {

                } else  if (!progressBarVShowDialog .isShowing()) {
                    progressBarVShowDialog.showDialog();
                }

            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                Log.e("yuyahao", "onProgressUpdate: " + values[0]);
            }

            @Override
            protected void onCancelled(Bitmap bitmap) {
                super.onCancelled(bitmap);
                Log.e("yuyahao", " : onCancelled");
                if (progressBarVShowDialog != null && progressBarVShowDialog.isShowing()) {
                    progressBarVShowDialog.disMymiss();
                }
                if (context != null) {
                    context = null;
                }
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                if (progressBarVShowDialog != null && progressBarVShowDialog.isShowing()) {
                    progressBarVShowDialog.disMymiss();
                }
                if (context != null) {
                    context = null;
                }
                Log.e("yuyahao", " : onCancelled");
            }
        }.execute(url);
    }
}
