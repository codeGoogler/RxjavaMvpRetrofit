package com.yuer.study.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.wang.avi.AVLoadingIndicatorView;
import com.yuer.study.R;

/**
 * Created by yuer on 2018/5/31.
 */

public class ProgressBarVShowDialog extends Dialog {

    private WindowManager wm;

    private LayoutInflater inflate;
    private View view;//加载的布局
    private Display d;
    private LinearLayout rl_dialog_content;
    private WindowManager.LayoutParams p;
    private Context context;
    private AVLoadingIndicatorView aiv_loading;
    private TextView tv_loading_tip;
    public ProgressBarVShowDialog(Context context) {
        super(context, R.style.dialog);
        init(context);
    }



    public ProgressBarVShowDialog(Context context, AttributeSet attrs) {
        super(context, R.style.dialog);
        // TODO Auto-generated constructor stub
        init(context);
    }
    public ProgressBarVShowDialog(Context context, AttributeSet attrs, int defStyle) {
        super(context, R.style.dialog);
        // TODO Auto-generated constructor stub
        init(context);
    }
    public ProgressBarVShowDialog setMyCanceledOnTouchOutside(boolean mCancelable) {
        setCanceledOnTouchOutside(mCancelable);
      return this;
    }

    public ProgressBarVShowDialog setLoadingTip(String tip) {
        tv_loading_tip.setText(tip);
        return this;
    }
    public ProgressBarVShowDialog setMyCancelable(boolean mCancelable) {
        OnKeyListener keylistener = new OnKeyListener(){
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode== KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        };

        setOnKeyListener(keylistener);
        setCancelable(mCancelable);
        return this;
    }
    public ProgressBarVShowDialog setLoadingTip(int  resColorId) {
        tv_loading_tip.setTextColor(resColorId);
        return this;
    }
    /****c初始化数据******/
    private void init(Context context) {
        setContentView(R.layout.avg_loading);
        tv_loading_tip = findViewById(R.id.tv_loading_tip);
        aiv_loading =  findViewById(R.id.aiv_loading);
        rl_dialog_content =  findViewById(R.id.rl_dialog_content);
        rl_dialog_content.setGravity(Gravity.CENTER);
        inflate = LayoutInflater.from(context);
        this.context = context;
        //setMyCircleDialogAtter(0.45, 0.8);
    }


    /**
     * 设置dialog的大小和位置 ,不显示黑色的window的背景
     */
    private void setDialogSize(){
        wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager m = getWindow().getWindowManager();
        d = m.getDefaultDisplay();  //为获取屏幕宽、高

        p = getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.45);   //高度设置为屏幕的1.0
        p.width = (int) (d.getWidth() * 0.8);    //宽度设置为屏幕的0.8
        p.alpha = 1.0f;      //设置本身透明度
        p.dimAmount = 0.0f;      //设置黑暗度

        getWindow().setAttributes(p);     //设置生效
        getWindow().setGravity(Gravity.CENTER);       //设置对齐.
    }
    /***设置dialog的宽高，带有背景黑色的*****/
    public ProgressBarVShowDialog setImDetailWIdth(double widthValue, double heithValue){
        ViewGroup.LayoutParams para = rl_dialog_content.getLayoutParams();
        WindowManager m = getWindow().getWindowManager();
        d = m.getDefaultDisplay();  //为获取屏幕宽、高
        p = getWindow().getAttributes();  //获取对话框当前的参数值
        para.height = (int) (d.getHeight() * heithValue);;
        para.width = (int) widthValue;
        rl_dialog_content.setLayoutParams(para);
        return this;

    }
    /***设置dialog的宽高，带有背景黑色的*****/
    public ProgressBarVShowDialog setDialogLayoutParams(int widthValue, int heithValue){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthValue,heithValue);
        params.gravity = Gravity.CENTER;
        aiv_loading.setLayoutParams(params);
        rl_dialog_content.setGravity(Gravity.CENTER);
        return this;

    }
    /***设置dialog的宽高，带有背景黑色的*****/
    public ProgressBarVShowDialog setMyCircleDialogAtter(double widthValue, double heithValue){
        ViewGroup.LayoutParams para = rl_dialog_content.getLayoutParams();
        WindowManager m = getWindow().getWindowManager();
        d = m.getDefaultDisplay();  //为获取屏幕宽、高
        p = getWindow().getAttributes();  //获取对话框当前的参数值
        para.height = (int) (d.getHeight() * heithValue);;
        para.width = (int) (d.getWidth() * widthValue);
        rl_dialog_content.setLayoutParams(para);
        return this;
    }
    /*
     设置dialog的大小和位置
    */
    public  void setDialogHigeht(double size){
        p.height = (int) (d.getHeight() * size);
        p.width = (int) (d.getWidth() * 0.8);    //宽度设置为屏幕的0.8
        p.alpha = 1.0f;      //设置本身透明度
        p.dimAmount = 0.0f;      //设置黑暗度

        getWindow().setAttributes(p);     //设置生效
        getWindow().setGravity(Gravity.CENTER);       //设置对齐
    }

   
    public ProgressBarVShowDialog showDialog(){
//        if(!getOwnerActivity().isFinishing()){
            if(!isShowing()){
                show();

//            }
        }
        return this;
    }
    public void disMymiss(){
//        if(!getOwnerActivity().isFinishing()){
            if(isShowing()){
                dismiss();
            }
        }
//    }
}
