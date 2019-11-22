package com.tu.entity;


import java.io.Serializable;
import java.sql.Timestamp;

public class Order implements Serializable {
    private Integer oId;

    private Integer uId;

    private String address;

    private Timestamp creatTime;

    private Double count;

    public Integer getoId() {
        return oId;
    }

    public void setoId(Integer oId) {
        this.oId = oId;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Timestamp getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Timestamp creatTime) {
        this.creatTime = creatTime;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Order{" +
                "oId=" + oId +
                ", uId=" + uId +
                ", address='" + address + '\'' +
                ", creatTime=" + creatTime +
                ", count=" + count +
                '}';
    }
}