package com.hualianzb.biut.models;

import java.io.Serializable;
import java.util.List;

/**
 * Date:2018/8/30
 * auther:wangtianyun
 * describe:
 */
public class TransRecordTimeRequestBean implements Serializable {
    /**
     * jsonrpc : 2.0
     * method : eth_getBlockByHash
     * params : ["0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331",true]
     * id : 1
     */

    private String jsonrpc;
    private String method;
    private int id;
    private List<Object> params;

    public TransRecordTimeRequestBean() {
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
