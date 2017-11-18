package com.example.fnweb.Entity;


import java.sql.Date;

/**
 * Created by ACER on 2017/11/3.
 */
public class UserLocationEntity {
    private int user_id;
    private int x;
    private int y;
    private Date time;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}
