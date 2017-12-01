package com.example.fnweb;

import com.example.fnweb.Entity.DeviceEntity;
import com.example.fnweb.Entity.RssiEntity;
import com.example.fnweb.Service.KNNService;
import com.example.fnweb.Service.NaiveBayesService;
import com.example.fnweb.Service.PointStoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FnwebApplicationTests {

	@Autowired
	private KNNService knnService;

	@Autowired
	private PointStoreService pointStoreService;

	@Autowired
	private NaiveBayesService naiveBayesService;

	@Test
	public void getArgsFromData(){
		System.out.println(naiveBayesService.getArgsFromData());
	}

	@Test
	public void getRssiInfo(){
		RssiEntity rssiEntity = new RssiEntity();
		rssiEntity.setAp1(1f);
		rssiEntity.setAp2(1f);
		rssiEntity.setAp3(1f);
		rssiEntity.setAp4(1f);
		rssiEntity.setAp5(1f);
		rssiEntity.setAp6(1f);
		rssiEntity.setAp7(1f);
		rssiEntity.setAp8(1f);
		rssiEntity.setAp9(1f);

		naiveBayesService.getLocByBayes(rssiEntity);
//		DeviceEntity deviceEntity = new DeviceEntity();
//		deviceEntity.setDevice_id(11);
//		knnService.getLocByKnn(rssiEntity,deviceEntity,5);
	}

	@Test
	public void insertPointLoc(){
		pointStoreService.insertPointLoc();
	}

}
