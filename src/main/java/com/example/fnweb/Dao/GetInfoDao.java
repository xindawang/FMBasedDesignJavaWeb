package com.example.fnweb.Dao;

import com.example.fnweb.Entity.UserLocationEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ACER on 2017/11/3.
 */

@Repository
public interface GetInfoDao {
    @Select("select * from user_location")
    List<UserLocationEntity> getAllLocation();
}
