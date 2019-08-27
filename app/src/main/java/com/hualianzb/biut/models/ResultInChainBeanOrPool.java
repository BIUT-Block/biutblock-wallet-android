package com.hualianzb.biut.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Date:2019/5/11
 * auther:wangtianyun
 * describe:
 */
@Entity
public class ResultInChainBeanOrPool {
    /**
     * TxHash : 7a69cf568a0f9731411d5ffe923cd789f689140b5199545e568e43b81fc62e0b
     * TxReceiptStatus : pending
     * Version :
     * TimeStamp : 1557569102514
     * TxFrom : 659c0673eaf00189f3dc6adfa96935c2eccb0933
     * TxTo : 69e061e5ebc42b23051ebe6fa9cc0bdf3010caae
     * Value : 10
     * GasLimit : 0
     * GasUsedByTxn : 0
     * GasPrice : 0
     * TxFee : 0.02
     * Nonce : 0
     * InputData :
     * Signature : {"r":"2a6e2a0bd21137fda9c29fefbe918b951d4f29db4b77e431228c1134d7797788","s":"4296cb2fb80fe0ba9dcd4aab81d6d66cc0a02af22cac1eb841ab55abf9cb2ee3","v":27}
     * TxHeight :
     */
    @Id
    private Long id;
    private String theAddress;

    private String TxHash;
    private String TxReceiptStatus;
    private long TimeStamp;
    private String TxFrom;
    private String TxTo;
    private String Value;
    private String TxFee;
    private String Nonce;
    private String BlockHash;
    private String InputData;
    private String TxHeight;
    private String type;
    private int blockNumber;

    @Generated(hash = 1681997268)
    public ResultInChainBeanOrPool(Long id, String theAddress, String TxHash, String TxReceiptStatus, long TimeStamp, String TxFrom, String TxTo, String Value, String TxFee,
            String Nonce, String BlockHash, String InputData, String TxHeight, String type, int blockNumber) {
        this.id = id;
        this.theAddress = theAddress;
        this.TxHash = TxHash;
        this.TxReceiptStatus = TxReceiptStatus;
        this.TimeStamp = TimeStamp;
        this.TxFrom = TxFrom;
        this.TxTo = TxTo;
        this.Value = Value;
        this.TxFee = TxFee;
        this.Nonce = Nonce;
        this.BlockHash = BlockHash;
        this.InputData = InputData;
        this.TxHeight = TxHeight;
        this.type = type;
        this.blockNumber = blockNumber;
    }

    @Generated(hash = 1438008723)
    public ResultInChainBeanOrPool() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTxHash() {
        return this.TxHash;
    }

    public void setTxHash(String TxHash) {
        this.TxHash = TxHash;
    }

    public String getTxReceiptStatus() {
        return this.TxReceiptStatus;
    }

    public void setTxReceiptStatus(String TxReceiptStatus) {
        this.TxReceiptStatus = TxReceiptStatus;
    }

    public long getTimeStamp() {
        return this.TimeStamp;
    }

    public void setTimeStamp(long TimeStamp) {
        this.TimeStamp = TimeStamp;
    }

    public String getTxFrom() {
        return this.TxFrom;
    }

    public void setTxFrom(String TxFrom) {
        this.TxFrom = TxFrom;
    }

    public String getTxTo() {
        return this.TxTo;
    }

    public void setTxTo(String TxTo) {
        this.TxTo = TxTo;
    }

    public String getValue() {
        return this.Value;
    }

    public void setValue(String Value) {
        this.Value = Value;
    }

    public String getTxFee() {
        return this.TxFee;
    }

    public void setTxFee(String TxFee) {
        this.TxFee = TxFee;
    }

    public String getNonce() {
        return this.Nonce;
    }

    public void setNonce(String Nonce) {
        this.Nonce = Nonce;
    }

    public String getBlockHash() {
        return this.BlockHash;
    }

    public void setBlockHash(String BlockHash) {
        this.BlockHash = BlockHash;
    }

    public String getInputData() {
        return this.InputData;
    }

    public void setInputData(String InputData) {
        this.InputData = InputData;
    }

    public String getTxHeight() {
        return this.TxHeight;
    }

    public void setTxHeight(String TxHeight) {
        this.TxHeight = TxHeight;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTheAddress() {
        return this.theAddress;
    }

    public void setTheAddress(String theAddress) {
        this.theAddress = theAddress;
    }

    public int getBlockNumber() {
        return this.blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

}
