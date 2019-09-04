package com.hualianzb.biut.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Date:2018/10/17
 * auther:wangtianyun
 * describe:
 */
@Entity
public class AddressBookBean {
    @Id
    private Long id;
    @NotNull
    private String name;
    private String address;
    private String creatTime;

    private String phone;
    private String email;
    private String remarks;
    @Generated(hash = 852870618)
    public AddressBookBean(Long id, @NotNull String name, String address,
            String creatTime, String phone, String email, String remarks) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.creatTime = creatTime;
        this.phone = phone;
        this.email = email;
        this.remarks = remarks;
    }
    @Generated(hash = 1311388237)
    public AddressBookBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCreatTime() {
        return this.creatTime;
    }
    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getRemarks() {
        return this.remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
