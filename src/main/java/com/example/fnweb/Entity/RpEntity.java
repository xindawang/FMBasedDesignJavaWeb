package com.example.fnweb.Entity;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by ACER on 2017/11/17.
 */
public class RpEntity {
    private Integer id;
    private Integer device_id;
    private HashMap<String, Float> apEntities;
    private String point_name;
    private Double knnResult;
    private Double x;
    private Double y;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Integer device_id) {
        this.device_id = device_id;
    }

    public String getPoint() {
        return point_name;
    }

    public void setPoint(String point) {
        this.point_name = point;
    }

    public Double getKnnResult() {
        return knnResult;
    }

    public void setKnnResult(Double knnResult) {
        this.knnResult = knnResult;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public HashMap<String, Float> getApEntities() {
        return apEntities;
    }

    public void setApEntities(HashMap<String, Float> apEntities) {
        this.apEntities = apEntities;
    }

    public String getLocString(){
        return this.getX()+","+this.getY();
    }
}
