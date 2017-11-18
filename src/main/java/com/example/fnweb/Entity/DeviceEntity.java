package com.example.fnweb.Entity;

import java.sql.Date;

/**
 * Created by ACER on 2017/11/17.
 */
public class DeviceEntity {
    private Integer device_id;
    private String device_name;
    private Date upload_time;

    public Integer getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Integer device_id) {
        this.device_id = device_id;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public Date getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(Date upload_time) {
        this.upload_time = upload_time;
    }

}
