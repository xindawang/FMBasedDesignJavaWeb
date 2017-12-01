package com.example.fnweb.Service;

/**
 * Created by ACER on 2017/11/17.
 */

import com.example.fnweb.Entity.DeviceEntity;
import com.example.fnweb.Entity.RssiEntity;
import com.example.fnweb.Mapper.DeviceMapper;
import com.example.fnweb.Mapper.RssiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
                RssiEntity rssiEntity = new RssiEntity();
                rssiEntity.setDevice_id(deviceId);
                rssiEntity.setPoint(str.trim());
                str=br.readLine();
                String [] eachPoint = str.split(";");
                for (int i = 0; i < eachPoint.length; i++) {
                    String [] eachRssi = eachPoint[i].split(",");
                    switch (eachRssi[0]){
                        case "abc1":rssiEntity.setAp1(Float.valueOf(eachRssi[1].trim()));break;
                        case "abc2":rssiEntity.setAp2(Float.valueOf(eachRssi[1].trim()));break;
                        case "abc3":rssiEntity.setAp3(Float.valueOf(eachRssi[1].trim()));break;
                        case "abc4":rssiEntity.setAp4(Float.valueOf(eachRssi[1].trim()));break;
                        case "abc5":rssiEntity.setAp5(Float.valueOf(eachRssi[1].trim()));break;
                        case "abc6":rssiEntity.setAp6(Float.valueOf(eachRssi[1].trim()));break;
                        case "abc7":rssiEntity.setAp7(Float.valueOf(eachRssi[1].trim()));break;
                        case "abc8":rssiEntity.setAp8(Float.valueOf(eachRssi[1].trim()));break;
                        case "abc9":rssiEntity.setAp9(Float.valueOf(eachRssi[1].trim()));break;
                    }
                }
                if (!rssiMapper.insertRssi(rssiEntity)) return false;
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