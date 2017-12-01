package com.example.fnweb.Mapper;

import com.example.fnweb.Entity.UserEntity;
import com.example.fnweb.Entity.UserLocationEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ACER on 2017/11/30.
 */
@Repository
public interface UserMapper {
    @Select("select * from user_location")
    List<UserLocationEntity> getAllLocations();

    @Select("select * from user")
    List<UserEntity> getAllUsers();
}
