package com.hualianzb.biut.models;

import java.io.Serializable;


/**
 * Date:2019/8/28
 * auther:wangtianyun
 * describe:
 */
public class NonceResult implements Serializable {
    /**
     * jsonrpc : 2.0
     * id : 1
     * result : {"status":"1","info":"OK","Nonce":"0"}
     */

    private String jsonrpc;
    private String id;
    private ResultBean result;

    public NonceResult() {
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * status : 1
         * info : OK
         * Nonce : 0
         */

        private String status;
        private String info;
        private String Nonce;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getNonce() {
            return Nonce;
        }

        public void setNonce(String Nonce) {
            this.Nonce = Nonce;
        }
    }
}
