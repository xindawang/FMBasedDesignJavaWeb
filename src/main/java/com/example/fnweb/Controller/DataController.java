package com.example.fnweb.Controller;

import com.example.fnweb.Mapper.DataMapper;
import com.example.fnweb.Service.DataStoreService;
import com.example.fnweb.tools.JsonTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ACER on 2017/11/3.
 */
@Controller
public class DataController {

    @Autowired
    private DataMapper dataMapper;

    @Autowired
    private DataStoreService dataStoreService;

    @RequestMapping(value = "/data",method = RequestMethod.GET)
    public String getDataSet(){
        return "iotMap";
    }

    @ResponseBody
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public String getAllUsername(){
        return JsonTool.listToJson(dataMapper.getAllUsers());
    }

    @ResponseBody
    @RequestMapping(value = "/user/loc",method = RequestMethod.GET)
    public String getAllUserLoc(){
        return JsonTool.listToJson(dataMapper.getAllLocations());
    }

    @ResponseBody
    @RequestMapping(value = "/data/store",method = RequestMethod.GET)
    public String storeData(){
        //step 1: receive data and store in server, return data path

        //step 2: import data from txt into database
        dataStoreService.insertData();
        return JsonTool.listToJson(dataMapper.getAllLocations());
    }

}
