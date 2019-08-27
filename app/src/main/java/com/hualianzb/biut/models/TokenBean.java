package com.hualianzb.biut.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Date:2018/8/28
 * auther:wangtianyun
 * describe:
 */
@Entity
public class TokenBean {
    @Id
    private Long id;
    @NotNull
    private String address;
    private String name;
    private String token;
    @Generated(hash = 1714312789)
    public TokenBean(Long id, @NotNull String address, String name, String token) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.token = token;
    }
    @Generated(hash = 1886787915)
    public TokenBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
