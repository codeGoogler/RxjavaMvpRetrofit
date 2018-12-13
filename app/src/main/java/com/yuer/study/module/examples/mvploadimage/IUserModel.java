package com.yuer.study.module.examples.mvploadimage;

import java.util.List;

/**
 * 类功能描述：</br>
 *
 * @author 于亚豪
 * @version 1.0 </p> 修改时间：2018/11/23</br> 修改备注：</br>
 */
public interface IUserModel {
    //加载用户信息的方法
    void loadUser(UserLoadListenner listener);
    //加载完成的回调
    interface UserLoadListenner{
        void complete(LoginBean users);
    }
}
