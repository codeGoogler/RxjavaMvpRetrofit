package com.yuer.study.module.examples.mvploadimage;

import com.yuer.study.bean.news.MultiNewsArticleBean;
import com.yuer.study.bean.wenda.WendaContentBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 类功能描述：</br>
 *
 * @author 于亚豪
 * @version 1.0 </p> 修改时间：2018/12/11</br> 修改备注：</br>
 */
public interface LoginAPi {
    String LOGIN_MVP_HOST = "https://www.apiopen.top/";

    //    用户注册接口 https://www.apiopen.top/createUser?key=00d91e8e0cca2b76f515926a36db68f5&phone=13594347817&passwd=123654
    //    用户登陆接口 https://www.apiopen.top/login?key=00d91e8e0cca2b76f515926a36db68f5&phone=13594347817&passwd=123456


    @GET("/login")
    Observable<LoginBean> login(
            @Query("key") String key,
            @Query("phone") String phone,
            @Query("passwd") String passwd);

    @FormUrlEncoded
    @POST("/login")
    Observable<LoginBean> loginPost(  @FieldMap Map<String,String> paramsMap);


    @POST("/login")
    @FormUrlEncoded
    Observable<LoginBean> loginPost(
            @Query("key") String key,
            @Query("phone") String phone,
            @Query("passwd") String passwd);

    @POST("https://www.apiopen.top/login")
    @FormUrlEncoded
    Observable<LoginBean> loginPost2(
            @Field("key") String key,
            @Field("phone") String phone,
            @Field("passwd") String passwd);

    @POST("https://www.apiopen.top/login?key=00d91e8e0cca2b76f515926a36db68f5&phone=13594347817&passwd=123456")
    Observable<LoginBean> loginPost2();
}
