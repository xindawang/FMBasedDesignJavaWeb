package com.example.fnweb.Service;

import com.example.fnweb.Entity.DeviceEntity;
import com.example.fnweb.Entity.RssiEntity;
import com.example.fnweb.Mapper.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ACER on 2017/11/18.
 */
@Service
public class KNNService {

    @Autowired
    private DataMapper dataMapper;

    public void getLocByKnn(RssiEntity rssiEntity,DeviceEntity deviceEntity, int k){
        RssiEntity[] rssiEntities = getMinK(rssiEntity,deviceEntity,5);
        double sum = 0, x=0, y=0;
        for (int i = 0; i <rssiEntities.length ; i++) sum += 1/rssiEntities[i].getKnnResult();
        for (int i = 0; i <rssiEntities.length ; i++) {
            x += rssiEntities[i].getKnnResult()/sum*rssiEntities[i].getX();
            y += rssiEntities[i].getKnnResult()/sum*rssiEntities[i].getY();
        }
        rssiEntity.setX(x);
        rssiEntity.setY(y);
        System.out.println();
    }

    public RssiEntity[] getMinK(RssiEntity rssiEntity,DeviceEntity deviceEntity, int k){
        //get rssi info from database according to the given device_id
        List<RssiEntity> rssiList = dataMapper.getRssiInfo(deviceEntity);

        //initialize big top heap
        int curNum = 0;
        RssiEntity[] minK = new RssiEntity[k];

        for (RssiEntity singleRssi : rssiList){
            double result = Math.sqrt(singleRssi.getAp1() - rssiEntity.getAp1())
                    + Math.sqrt(singleRssi.getAp2() - rssiEntity.getAp2())
                    + Math.sqrt(singleRssi.getAp3() - rssiEntity.getAp3())
                    + Math.sqrt(singleRssi.getAp4() - rssiEntity.getAp4())
                    + Math.sqrt(singleRssi.getAp5() - rssiEntity.getAp5())
                    + Math.sqrt(singleRssi.getAp6() - rssiEntity.getAp6())
                    + Math.sqrt(singleRssi.getAp7() - rssiEntity.getAp7())
                    + Math.sqrt(singleRssi.getAp8() - rssiEntity.getAp8())
                    + Math.sqrt(singleRssi.getAp9() - rssiEntity.getAp9());
            singleRssi.setKnnResult(result);
            if(curNum < k) minK[curNum++] = singleRssi;
            else if (singleRssi.getKnnResult() < minK[0].getKnnResult()){
                minK[0] = singleRssi;

                //sort the array by big top heap(using method of array)
                minK =maxHeapify(minK, 0, k-1);
            }
            System.out.println(result);
        }
        return minK;
    }

    private RssiEntity[] maxHeapify(RssiEntity[] rssiEntities,int index,int len){
        int li = (index << 1) + 1; // 左子节点索引
        int ri = li + 1;           // 右子节点索引
        int cMax = li;             // 子节点值最大索引，默认左子节点。

        if(li > len) return rssiEntities;       // 左子节点索引超出计算范围，直接返回。
        if(ri <= len && rssiEntities[ri].getKnnResult() > rssiEntities[li].getKnnResult()) // 先判断左右子节点，哪个较大。
            cMax = ri;
        if(rssiEntities[cMax].getKnnResult() > rssiEntities[index].getKnnResult()){
            RssiEntity tmpRssiEntity = rssiEntities[index];
            rssiEntities[index] = rssiEntities[cMax];
            rssiEntities[cMax] = tmpRssiEntity;
            rssiEntities = maxHeapify(rssiEntities,cMax, len);  // 则需要继续判断换下后的父节点是否符合堆的特性。
        }
        return rssiEntities;
    }
}
