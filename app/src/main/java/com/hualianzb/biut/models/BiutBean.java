package com.hualianzb.biut.models;

/**
 * Date:2019/6/28
 * auther:wangtianyun
 * describe:
 */
public class BiutBean {

    /**
     * privateKey : 6e7d9072dd9a3d7003c0124b11728137d90943a044fa753e65f828428340d716
     * publicKey : b0373d4b3fa03b4d0f4314b094fb3288f76a1901c7192a55013d01b03ac45d86ef155a2b07ea13e82314f4031b6dfeee183314c8ead6253b304a18c38bfdb5a8
     * englishWords : hover uncle bronze robot phrase retreat audit across enrich merit park hurry mountain peace library palm inside veteran lawn explain choice parrot fox scan
     * userAddress : 8818d76780d1e955488a474ae417945b1e346aa2
     */

    private String privateKey;
    private String publicKey;
    private String englishWords;
    private String userAddress;
    private String walletName;

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getEnglishWords() {
        return englishWords;
    }

    public void setEnglishWords(String englishWords) {
        this.englishWords = englishWords;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
}
