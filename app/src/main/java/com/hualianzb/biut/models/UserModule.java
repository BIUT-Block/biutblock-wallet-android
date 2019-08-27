package com.hualianzb.biut.models;

import com.hualianzb.biut.application.BIUTApplication;

import java.io.Serializable;

/**
 * 用户模块 json解析
 * Created by Administrator on 2015/12/7 0007.
 */
public class UserModule implements Serializable {
    private static UserModule instance = null;

    public UserModule() {
    }

    public static UserModule getInstance() {
        if (instance == null) {
            synchronized (BIUTApplication.class) {
                if (instance == null) {
                    instance = new UserModule();
                }
            }
        }
        return instance;
    }

    public static void setInstance(UserModule instance) {
        UserModule.instance = instance;
    }


}
