package com.example.fnweb.Controller;

import com.example.fnweb.Mapper.UserMapper;
import com.example.fnweb.tools.JsonTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ACER on 2017/11/30.
 */
@Controller
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @ResponseBody
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public String getAllUsername(){
        return JsonTool.listToJson(userMapper.getAllUsers());
    }

    @ResponseBody
    @RequestMapping(value = "/user/loc",method = RequestMethod.GET)
    public String getAllUserLoc(){
        return JsonTool.listToJson(userMapper.getAllLocations());
    }
}
