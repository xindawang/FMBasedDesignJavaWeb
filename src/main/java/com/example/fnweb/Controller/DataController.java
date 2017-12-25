package com.example.fnweb.Controller;

import com.example.fnweb.Entity.ApEntity;
import com.example.fnweb.Entity.DeviceEntity;
import com.example.fnweb.Entity.RpEntity;
import com.example.fnweb.Mapper.DeviceMapper;
import com.example.fnweb.Mapper.PointLocMapper;
import com.example.fnweb.Service.DataStoreService;
import com.example.fnweb.Service.KNNService;
import com.example.fnweb.tools.JsonTool;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileReader;
import java.util.*;

/**
 * Created by ACER on 2017/11/3.
 */
@Controller
public class DataController {

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private PointLocMapper pointLocMapper;

    @Autowired
    private DataStoreService dataStoreService;

    @Autowired
    private KNNService knnService;

    @RequestMapping(value = "/data",method = RequestMethod.GET)
    public String getDataSet(){
        return "iotMap";
    }


    @ResponseBody
    @RequestMapping(value = "/data/device",method = RequestMethod.GET)
    public String getDevicesInfo(int pageSize, int page) {
        PageHelper.startPage(page, pageSize);
        List<DeviceEntity> list = deviceMapper.getAllDevicesInfo();
        long total = ((Page<DeviceEntity>) list).getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);
        return JsonTool.objectToJson(map);
    }

    @ResponseBody
    @RequestMapping(value = "/data/store",method = RequestMethod.GET)
    public String storeData(){
        //step 1: receive data and store in server, return data path

        //step 2: import data from txt into database
        dataStoreService.insertData();
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/loc",method = RequestMethod.POST)
    public String getUserLoc(ApEntity apEntity) {
        RpEntity rpEntity = new RpEntity();
        HashMap<String,Float> apentities = new HashMap<>();
        if (apEntity.getAbc1()!=null) apentities.put("abc1",Float.valueOf(apEntity.getAbc1()));
        if (apEntity.getAbc2()!=null) apentities.put("abc2",Float.valueOf(apEntity.getAbc2()));
        if (apEntity.getAbc3()!=null) apentities.put("abc3",Float.valueOf(apEntity.getAbc3()));
        if (apEntity.getAbc4()!=null) apentities.put("abc4",Float.valueOf(apEntity.getAbc4()));
        if (apEntity.getAbc5()!=null) apentities.put("abc5",Float.valueOf(apEntity.getAbc5()));
        if (apEntity.getAbc6()!=null) apentities.put("abc6",Float.valueOf(apEntity.getAbc6()));
        if (apEntity.getAbc7()!=null) apentities.put("abc7",Float.valueOf(apEntity.getAbc7()));
        if (apEntity.getAbc8()!=null) apentities.put("abc8",Float.valueOf(apEntity.getAbc8()));
        if (apEntity.getAbc9()!=null) apentities.put("abc9",Float.valueOf(apEntity.getAbc9()));
        rpEntity.setApEntities(apentities);
        knnService.getLocByKnn(rpEntity,null);
        return rpEntity.getLocString();
    }
}
