package com.example.fnweb.Mapper;

import com.example.fnweb.Entity.BayesArgsEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ACER on 2017/11/30.
 */
@Repository
public interface BayesMapper {
    @Select("select count(id) from bayes_args where point_name = #{pointName}")
    Boolean hasPoint(String pointName);

    @Select("select distinct point_name from bayes_args")
    List<String> getAllPointName();

    @Select("select ${apNameAvg} as apNameAvg,${apNameVar} as apNameVar from bayes_args where point_name = #{pointName}")
    BayesArgsEntity getEachApArgs(@Param("apNameAvg") String apNameAvg, @Param("apNameVar") String apNameVar,
                                        @Param("pointName") String pointName);

    @Insert("insert into bayes_args (point_name,${apNameAvg},${apNameVar}) " +
            "values (#{pointName},#{apAvg},#{apVar})")
    Boolean insertBayesArgs(@Param("apNameAvg") String apNameAvg, @Param("apNameVar") String apNameVar,
                            @Param("pointName") String pointName, @Param("apAvg") double apAvg, @Param("apVar") double apVar);

    @Update("update bayes_args set ${apNameAvg}=#{apAvg},${apNameVar}=#{apVar}" +
            "where point_name = #{pointName}")
    Boolean updateBayesArgs(@Param("apNameAvg") String apNameAvg,@Param("apNameVar") String apNameVar,
                            @Param("pointName") String pointName,@Param("apAvg") double apAvg,@Param("apVar") double apVar);
}
