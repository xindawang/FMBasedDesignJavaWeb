package com.example.fnweb.Controller;

import com.example.fnweb.Entity.DeviceEntity;
import com.example.fnweb.Mapper.DeviceMapper;
import com.example.fnweb.Mapper.PointLocMapper;
import com.example.fnweb.Service.DataStoreService;
import com.example.fnweb.tools.JsonTool;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

}
