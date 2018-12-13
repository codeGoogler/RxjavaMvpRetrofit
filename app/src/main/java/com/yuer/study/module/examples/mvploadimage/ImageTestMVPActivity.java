package com.yuer.study.module.examples.mvploadimage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.stetho.common.LogUtil;
import com.yuer.study.R;

import java.util.List;

/**
 * 类功能描述：</br>
 *
 * @author 于亚豪
 * @version 1.0 </p> 修改时间：2018/11/23</br> 修改备注：</br>
 */
public class ImageTestMVPActivity extends   /*FragmentActivity IBaseActivity<IUserVirew,ImageListPresent>*/     IBaseActivity<IUserVirew,ImageListPresent> implements IUserVirew,View.OnClickListener{
    ImageListPresent imageListPresent = null;

    EditText et_password;
    EditText et_account;
    Button btn_cancle;
    Button btn_ok;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//

        et_password = findViewById(R.id.et_password);
        et_account = findViewById(R.id.et_account);
        btn_cancle = findViewById(R.id.btn_cancle);
        btn_ok = findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);

//         imageListPresent = new ImageListPresent(this);

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void showUser(LoginBean loginBean) {
        LogUtil.e("z最终显示的结果:      ",loginBean.toString());
        Toast.makeText(getApplicationContext(),loginBean.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadData() {
        //
    }


    @Override
    protected ImageListPresent createPresenter() {
        imageListPresent = new ImageListPresent(this);
        return imageListPresent;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ok:

                imageListPresent.load();
                break;
        }
    }
}
