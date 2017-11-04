package com.example.fnweb.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ACER on 2017/11/3.
 */
@Controller
public class DataController {

    @RequestMapping(value = "data/getAll",method = RequestMethod.GET)
    public String getDataSet(){
        return "iotMap";
    }
}
