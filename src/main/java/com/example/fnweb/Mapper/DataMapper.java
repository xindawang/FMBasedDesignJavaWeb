package com.example.fnweb.Mapper;

import com.example.fnweb.Entity.DeviceEntity;
import com.example.fnweb.Entity.RssiEntity;
import com.example.fnweb.Entity.UserEntity;
import com.example.fnweb.Entity.UserLocationEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ACER on 2017/11/3.
 */

@Repository
public interface DataMapper {
    @Select("select * from user_location")
    List<UserLocationEntity> getAllLocations();

    @Select("select * from user")
    List<UserEntity> getAllUsers();

    @Select("select * from device_info")
    List<DeviceEntity> getAllDevicesInfo();

    @Insert("insert into device_info set device_name=#{device_name}")
    @Options(useGeneratedKeys=true, keyProperty="device_id")
    Integer insertDevice(DeviceEntity deviceEntity);

    @Insert("insert into rssi_info(device_id,point,ap1,ap2,ap3,ap4,ap5,ap6,ap7,ap8,ap9)values " +
            "(#{device_id},#{point},#{ap1},#{ap2},#{ap3},#{ap4},#{ap5},#{ap6},#{ap7},#{ap8},#{ap9})")
    Boolean insertRssi(RssiEntity rssiEntity);
}
