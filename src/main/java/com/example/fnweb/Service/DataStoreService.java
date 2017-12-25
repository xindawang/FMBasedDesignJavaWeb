package com.example.fnweb.Service;

/**
 * Created by ACER on 2017/11/17.
 */

import com.example.fnweb.Entity.DeviceEntity;
import com.example.fnweb.Entity.RpEntity;
import com.example.fnweb.Mapper.DeviceMapper;
import com.example.fnweb.Mapper.RssiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

@Service
public class DataStoreService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private RssiMapper rssiMapper;

    private static String path = "E:\\IndoorLocation\\FengNiao\\FMWeb\\src\\main\\resources\\static\\data\\mi.txt";

    @Transactional
    public boolean insertData(){
        DeviceEntity deviceEntity = new DeviceEntity();

        //step 1: insert into device_info table
        deviceEntity.setDevice_name("mi");
        deviceMapper.insertDevice(deviceEntity);

        //step 2: set entity and insert into rssi_info table
        return setRssiEntityAndInsert(deviceEntity.getDevice_id());
    }


    public boolean setRssiEntityAndInsert(Integer deviceId){
        try {
            FileReader reader = new FileReader(path);
            BufferedReader br = new BufferedReader(reader);
            String str=br.readLine();
            while(str != null&& !str.contains("*")) {
                RpEntity rpEntity = new RpEntity();
                rpEntity.setDevice_id(deviceId);
                rpEntity.setPoint(str.trim());
                str=br.readLine();
                String [] eachPoint = str.split(";");
                HashMap<String, Float> apEntities = new HashMap<>();
                for (int i = 0; i < eachPoint.length; i++) {
                    String [] eachRssi = eachPoint[i].split(",");
                    switch (eachRssi[0]){
                        case "abc1":
                            apEntities.put("ap1",Float.valueOf(eachRssi[1].trim()));break;
                        case "abc2":
                            apEntities.put("ap2",Float.valueOf(eachRssi[1].trim()));break;
                        case "abc3":
                            apEntities.put("ap3",Float.valueOf(eachRssi[1].trim()));break;
                        case "abc4":
                            apEntities.put("ap4",Float.valueOf(eachRssi[1].trim()));break;
                        case "abc5":
                            apEntities.put("ap5",Float.valueOf(eachRssi[1].trim()));break;
                        case "abc6":
                            apEntities.put("ap6",Float.valueOf(eachRssi[1].trim()));break;
                        case "abc7":
                            apEntities.put("ap7",Float.valueOf(eachRssi[1].trim()));break;
                        case "abc8":
                            apEntities.put("ap8",Float.valueOf(eachRssi[1].trim()));break;
                        case "abc9":
                            apEntities.put("ap9",Float.valueOf(eachRssi[1].trim()));break;
                    }
                }
                if (!rssiMapper.insertRssi(rpEntity)) return false;
                str=br.readLine();
            }

            br.close();
            reader.close();
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}