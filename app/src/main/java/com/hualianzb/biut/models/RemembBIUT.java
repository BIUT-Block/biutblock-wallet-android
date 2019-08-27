package com.hualianzb.biut.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class RemembBIUT {

    public RemembBIUT() {
    }
    @Generated(hash = 340210172)
    public RemembBIUT(Long id, String walletName, String address, String pass,
            String mnemonics, String tips, String privateKey, String publicKey,
            int walletincon, boolean isNow, boolean isBackup, int howToCreate,
            String creatTime) {
        this.id = id;
        this.walletName = walletName;
        this.address = address;
        this.pass = pass;
        this.mnemonics = mnemonics;
        this.tips = tips;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.walletincon = walletincon;
        this.isNow = isNow;
        this.isBackup = isBackup;
        this.howToCreate = howToCreate;
        this.creatTime = creatTime;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getWalletName() {
        return this.walletName;
    }
    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPass() {
        return this.pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getMnemonics() {
        return this.mnemonics;
    }
    public void setMnemonics(String mnemonics) {
        this.mnemonics = mnemonics;
    }
    public String getTips() {
        return this.tips;
    }
    public void setTips(String tips) {
        this.tips = tips;
    }
    public String getPrivateKey() {
        return this.privateKey;
    }
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
    public String getPublicKey() {
        return this.publicKey;
    }
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
    public int getWalletincon() {
        return this.walletincon;
    }
    public void setWalletincon(int walletincon) {
        this.walletincon = walletincon;
    }
    public boolean getIsNow() {
        return this.isNow;
    }
    public void setIsNow(boolean isNow) {
        this.isNow = isNow;
    }
    public boolean getIsBackup() {
        return this.isBackup;
    }
    public void setIsBackup(boolean isBackup) {
        this.isBackup = isBackup;
    }
    public int getHowToCreate() {
        return this.howToCreate;
    }
    public void setHowToCreate(int howToCreate) {
        this.howToCreate = howToCreate;
    }
    public String getCreatTime() {
        return this.creatTime;
    }
    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    @Id
    private Long id;
    private String walletName;
    private String address;
    private String pass;
    private String mnemonics;
    private String tips;
    private String privateKey;
    private String publicKey;
    private int walletincon;//钱包头像
    private boolean isNow;//是否当前钱包
    private boolean isBackup;//是否已经备份
    private int howToCreate;//1，创建的  2助记词导入的 3私钥导入的，4 keystore导入的
    private String creatTime;
}
