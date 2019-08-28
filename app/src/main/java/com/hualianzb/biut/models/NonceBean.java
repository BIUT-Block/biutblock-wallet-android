package com.hualianzb.biut.models;

import java.io.Serializable;
import java.util.List;

/**
 * Date:2019/8/28
 * auther:wangtianyun
 * describe:
 */
public class NonceBean implements Serializable {
    public NonceBean() {
    }

    private String method;
    private List<String> params;
    private String jsonrpc;
    private String id;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
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

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }
}
