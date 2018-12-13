package com.yuer.study.module.examples.mvploadimage;

import java.util.List;

/**
 * 类功能描述：</br>
 *
 * @author 于亚豪
 * @version 1.0 </p> 修改时间：2018/11/23</br> 修改备注：</br>
 */
public interface IUserVirew  extends IView {
    //显示进度条
    void showLoading();
    //展示用户数据
    void showUser(LoginBean users);



}
