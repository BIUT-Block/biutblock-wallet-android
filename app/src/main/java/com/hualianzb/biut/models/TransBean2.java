package com.hualianzb.biut.models;

import java.io.Serializable;

/**
 * Date:2019/5/6
 * auther:wangtianyun
 * describe:
 */
public class TransBean2 implements Serializable {

    public TransBean2() {
    }

    /**
     * timestamp : 1557143586369
     * from : e27075290aeeffeb943ec5fd23fe834e4d4499e1
     * to : a918f160a6b951991ce9290ce7be9b72a2b5c55b
     * value : 5
     * gasLimit : 0
     * gas : 0
     * gasPrice : 0
     * inputData :
     * data : {"v":27,"r":"2a15d8609d4b8d695f7cbe7969a13737ef8295bba2302d922220a38a7544cc11","s":"5e56c3708f22d1c0a984106dd90cb849c1debad7ebacc28877637c905dba499f"}
     */

    private long timestamp;
    private String from;
    private String to;
    private String value;
    private String gasLimit;
    private String TxFee;
    private String gasPrice;
    private String inputData;
    private DataBean data;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getGasLimit() {
        return gasLimit;
    }

    public void setGasLimit(String gasLimit) {
        this.gasLimit = gasLimit;
    }

    public String getTxFee() {
        return TxFee;
    }

    public void setTxFee(String TxFee) {
        this.TxFee = TxFee;
    }

    public String getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(String gasPrice) {
        this.gasPrice = gasPrice;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * v : 27
         * r : 2a15d8609d4b8d695f7cbe7969a13737ef8295bba2302d922220a38a7544cc11
         * s : 5e56c3708f22d1c0a984106dd90cb849c1debad7ebacc28877637c905dba499f
         */

        private int v;
        private String r;
        private String s;

        public int getV() {
            return v;
        }

        public void setV(int v) {
            this.v = v;
        }

        public String getR() {
            return r;
        }

        public void setR(String r) {
            this.r = r;
        }

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }
    }
}
