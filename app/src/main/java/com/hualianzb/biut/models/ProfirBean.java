package com.hualianzb.biut.models;

import java.io.Serializable;

/**
 * Date:2019/8/13
 * auther:wangtianyun
 * describe:
 */
public class ProfirBean implements Serializable {
    private String date = "05/26/2019 16:25:08 UTC +8";
    private String profit = "123456.12";

    public ProfirBean() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }
}
