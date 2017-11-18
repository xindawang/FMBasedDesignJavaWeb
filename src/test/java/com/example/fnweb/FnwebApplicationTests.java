package com.example.fnweb;

import com.example.fnweb.Entity.DeviceEntity;
import com.example.fnweb.Entity.RssiEntity;
import com.example.fnweb.Mapper.DataMapper;
import com.example.fnweb.Service.DataStoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FnwebApplicationTests {

	@Autowired
	private DataStoreService dataStoreService;

//	@Test
//	public void contextLoads() {
//		DeviceEntity deviceEntity = new DeviceEntity();
//		String path = "E:\\IndoorLocation\\FengNiao\\FMWeb\\src\\main\\resources\\static\\data\\mi.txt";
//
//
//		//step 1: insert into device_info table
//		deviceEntity.setDevice_name("mi");
//		dataMapper.insertDevice(deviceEntity);
//
//		//step 2: set entity and insert into rssi_info table
//		DataStoreService dataStoreService = new DataStoreService();
//		try {
//			FileReader reader = new FileReader(path);
//			BufferedReader br = new BufferedReader(reader);
//			String str=br.readLine();
//			while(str != null&& !str.contains("*")) {
//				RssiEntity rssiEntity = new RssiEntity();
//				rssiEntity.setDevice_id(deviceEntity.getDevice_id());
//				rssiEntity.setPoint(str.trim());
//				str=br.readLine();
//				String [] eachPoint = str.split(";");
//				for (int i = 0; i < eachPoint.length; i++) {
//					String [] eachRssi = eachPoint[i].split(",");
//					switch (eachRssi[0]){
//						case "abc1":rssiEntity.setAp1(Float.valueOf(eachRssi[1].trim()));break;
//						case "abc2":rssiEntity.setAp2(Float.valueOf(eachRssi[1].trim()));break;
//						case "abc3":rssiEntity.setAp3(Float.valueOf(eachRssi[1].trim()));break;
//						case "abc4":rssiEntity.setAp4(Float.valueOf(eachRssi[1].trim()));break;
//						case "abc5":rssiEntity.setAp5(Float.valueOf(eachRssi[1].trim()));break;
//						case "abc6":rssiEntity.setAp6(Float.valueOf(eachRssi[1].trim()));break;
//						case "abc7":rssiEntity.setAp7(Float.valueOf(eachRssi[1].trim()));break;
//						case "abc8":rssiEntity.setAp8(Float.valueOf(eachRssi[1].trim()));break;
//						case "abc9":rssiEntity.setAp9(Float.valueOf(eachRssi[1].trim()));break;
//
//					}
//				}
//				if (!dataMapper.insertRssi(rssiEntity)) System.out.println(false);
//				str=br.readLine();
//			}
//
//			br.close();
//			reader.close();
//		}
//		catch(FileNotFoundException e) {
//			e.printStackTrace();
//		}catch(IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println(true);
//	}

	@Test
	public void testService(){
		dataStoreService.insertData();
	}

}
