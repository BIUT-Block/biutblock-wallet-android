package com.hualianzb.biut.models;

import java.io.Serializable;

/**
 * Date:2019/4/29
 * auther:wangtianyun
 * describe:
 */
public class ReceiptBean implements Serializable {
    private int type;//0  biut  1 biu
    private String address;
    private String value;

    public ReceiptBean() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
