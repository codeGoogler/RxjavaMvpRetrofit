package com.yuer.study.module.examples.mvploadimage;

/**
 * 类功能描述：</br>
 *
 * @author 于亚豪
 * @version 1.0 </p> 修改时间：2018/11/23</br> 修改备注：</br>
 */
public abstract class IBaseBean<T extends IBaseBean> {
    private String message ;
    private int code ;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
