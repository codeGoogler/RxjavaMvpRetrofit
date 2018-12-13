package com.yuer.study.module.examples.mvploadimage;

import java.util.List;

/**
 * 类功能描述：</br>
 *
 * @author 于亚豪
 * @version 1.0 </p> 修改时间：2018/11/23</br> 修改备注：</br>
 * https://blog.csdn.net/u012124438/article/details/51627220
 */
public class ImageListPresent extends  BasePresenter<IUserVirew>{

    IUserVirew iView;

    public ImageListPresent(IUserVirew iView) {
        this.iView = iView;
    }



    //view
    //IUserView mUserView;
    //model
    IUserModel mUserModel = new UserModelImpl();

    /*public GridPresenter(IUserView mUserView) {
        super();
        this.mUserView = mUserView;
    }*/
    //加载数据
    public void load() {
        //加载进度条
        //mUserView.showLoading();
        try{

            /*if(getView() != null){
            getView().showLoading();
            }else{
                iView.showLoading();
            }*/

            getView().showLoading();
            //model进行数据获取
            if (mUserModel != null) {
                mUserModel.loadUser(new IUserModel.UserLoadListenner() {
                    @Override
                    public void complete(LoginBean users) {
                        // 数据加载完后进行回调，交给view进行展示
                        //mUserView.showUser(users);
                        getView().showUser(users);
//                        iView.showUser(users);
                    }
                });

            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
