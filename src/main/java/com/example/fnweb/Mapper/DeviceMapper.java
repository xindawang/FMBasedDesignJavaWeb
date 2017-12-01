package com.example.fnweb.Mapper;

import com.example.fnweb.Entity.*;
import com.example.fnweb.Service.PointStoreService;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ACER on 2017/11/3.
 */

@Repository
public interface DeviceMapper {
    @Select("select * from device_info")
    List<DeviceEntity> getAllDevicesInfo();

    @Insert("insert into device_info set device_name=#{device_name}")
    @Options(useGeneratedKeys=true, keyProperty="device_id")
    Integer insertDevice(DeviceEntity deviceEntity);
}
