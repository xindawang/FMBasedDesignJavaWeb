package com.example.fnweb.Mapper;

import com.example.fnweb.Entity.DeviceEntity;
import com.example.fnweb.Entity.RssiEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ACER on 2017/11/30.
 */
@Repository
public interface RssiMapper {
    @Select("select * from rssi_info where device_id=#{device_id}")
    List<RssiEntity> getRssiInfoByDeviceId(DeviceEntity deviceEntity);

    @Select("select * from rssi_info where point_name=#{pointName}")
    List<RssiEntity> getRssiInfoByPointName(String pointName);

    @Select("select ${apName} from rssi_info where point_name = #{pointName}")
    List<Double> getEachApRssiByPointName(@Param("apName") String apName , @Param("pointName") String pointName);

    @Insert("insert into rssi_info(device_id,point_name,ap1,ap2,ap3,ap4,ap5,ap6,ap7,ap8,ap9)values " +
            "(#{device_id},#{point_name},#{ap1},#{ap2},#{ap3},#{ap4},#{ap5},#{ap6},#{ap7},#{ap8},#{ap9})")
    Boolean insertRssi(RssiEntity rssiEntity);
}
