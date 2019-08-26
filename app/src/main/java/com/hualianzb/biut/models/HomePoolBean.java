package com.hualianzb.biut.models;

import java.io.Serializable;

/**
 * Date:2019/8/5
 * auther:wangtianyun
 * describe:
 */
public class HomePoolBean implements Serializable {
    private String name = "白蛇爱上小青了";
    private String dayprofit = "1.23456789";
    private boolean isJion ;

    public HomePoolBean(boolean isJion) {
        this.isJion = isJion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDayprofit() {
        return dayprofit;
    }

    public void setDayprofit(String dayprofit) {
        this.dayprofit = dayprofit;
    }

    public boolean isJion() {
        return isJion;
    }

    public void setJion(boolean jion) {
        isJion = jion;
    }
}
