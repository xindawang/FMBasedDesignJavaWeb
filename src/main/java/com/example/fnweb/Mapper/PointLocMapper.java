package com.example.fnweb.Mapper;

import com.example.fnweb.Entity.PointLocEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ACER on 2017/11/30.
 */
@Repository
public interface PointLocMapper {
    @Select("select * from point_loc where point_name=#{pointName}")
    PointLocEntity getPointLocInfoByName(String pointName);

    @Select("select * from point_loc2 where point_name=#{pointName}")
    PointLocEntity getPointLoc2InfoByName(String pointName);

    @Select("select * from point_loc where point_name=#{point_name}")
    PointLocEntity getPointLocInfoByEntity(PointLocEntity pointLocEntity);

    @Select("select distinct point_name from point_loc")
    List<String> getAllPointName();

    @Insert("insert into point_loc (point_name,x,y) values (#{point_name},#{x},#{y})")
    Boolean insertPointLoc(PointLocEntity pointLocEntity);

    @Insert("insert into point_loc2 (point_name,x,y) values (#{point_name},#{x},#{y})")
    Boolean insertPointLoc2(PointLocEntity pointLocEntity);
}
