package com.example.fnweb.Service;

import com.example.fnweb.Entity.DeviceEntity;
import com.example.fnweb.Entity.PointLocEntity;
import com.example.fnweb.Entity.RpEntity;
import com.example.fnweb.Mapper.DeviceMapper;
import com.example.fnweb.Mapper.PointLocMapper;
import com.example.fnweb.Mapper.RssiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ACER on 2017/11/18.
 */
@Service
public class KNNService {

    @Autowired
    private DeviceMapper dataMapper;

    @Autowired
    private RssiMapper rpMapper;

    @Autowired
    private PointLocMapper pointLocMapper;

    private int k = 5;

    boolean useDatabase = false;

    public void getLocByKnn(RpEntity rpEntity, DeviceEntity deviceEntity){

        //appoint the number of minimum AP point
        List<RpEntity> rpList;
        if (useDatabase)  rpList= rpMapper.getRssiInfoByDeviceId(deviceEntity);
        else rpList = getRssiEntityFromTxt("d:\\data.txt");
        RpEntity[] rpEntities = getMinK(rpEntity,rpList,5);
        PointLocEntity[] pointLocEntities = getRelPointInfo(rpEntities,k);
        double sum = 0, x=0, y=0;

        //calculate the sum of k rp
        for (int i = 0; i <rpEntities.length ; i++) {
            sum += 1/rpEntities[i].getKnnResult();
        }

        //get ratio and location info of each point and add up for the result
        for (int i = 0; i <rpEntities.length ; i++) {
            double ratio = 1/rpEntities[i].getKnnResult()/sum;
            int point_x = pointLocEntities[i].getX();
            int point_y = pointLocEntities[i].getY();
            x += ratio * point_x;
            y += ratio * point_y;
        }

        //convert the format of location info according to how it store into database
        double result_x = x/Math.pow(10,7) + 12735839;
        double result_y = y/Math.pow(10,7) + 3569534;
        rpEntity.setX(result_x);
        rpEntity.setY(result_y);
    }

    //get all the point location information from database
    private PointLocEntity[] getRelPointInfo(RpEntity[] rpEntities, int k) {
        PointLocEntity[] pointLocEntity = new PointLocEntity[k];
        int i = 0 ;
        for (RpEntity rpEntity :rpEntities) {
            pointLocEntity[i++]=pointLocMapper.getPointLoc2InfoByName(rpEntity.getPoint());
        }
        return pointLocEntity;
    }

    public RpEntity[] getMinK(RpEntity rpEntity, List<RpEntity> rpEntityList, int k){
        //get rp info from database according to the given device_id

        //initialize big top heap
        int curNum = 0;
        RpEntity[] minK = new RpEntity[k];

        for (RpEntity singleRp : rpEntityList){
            double result = 0;
            for (String apName: singleRp.getApEntities().keySet()) {
                result += Math.sqrt(Math.abs(singleRp.getApEntities().get(apName) - rpEntity.getApEntities().get(apName)));
            }

            singleRp.setKnnResult(result);
            if(curNum < k) minK[curNum++] = singleRp;
            else if (singleRp.getKnnResult() < minK[0].getKnnResult()){
                minK[0] = singleRp;

                //sort the array by big top heap(using method of array)
                minK =maxHeapify(minK, 0, k-1);
            }
//            System.out.println(result);
        }
        return minK;
    }

    //similar to heap rank
    private RpEntity[] maxHeapify(RpEntity[] rpEntities, int index, int len){
        int li = (index << 1) + 1; // 左子节点索引
        int ri = li + 1;           // 右子节点索引
        int cMax = li;             // 子节点值最大索引，默认左子节点。

        if(li > len) return rpEntities;       // 左子节点索引超出计算范围，直接返回。
        if(ri <= len && rpEntities[ri].getKnnResult() > rpEntities[li].getKnnResult()) // 先判断左右子节点，哪个较大。
            cMax = ri;
        if(rpEntities[cMax].getKnnResult() > rpEntities[index].getKnnResult()){
            RpEntity tmpRpEntity = rpEntities[index];
            rpEntities[index] = rpEntities[cMax];
            rpEntities[cMax] = tmpRpEntity;
            rpEntities = maxHeapify(rpEntities,cMax, len);  // 则需要继续判断换下后的父节点是否符合堆的特性。
        }
        return rpEntities;
    }

    private List<RpEntity> getRssiEntityFromTxt(String filename){
        List<RpEntity> rpEntities = new ArrayList<>();
        try {
            FileReader reader = new FileReader(filename);
            BufferedReader br = new BufferedReader(reader);
            String str = br.readLine();
            int count = 1;
            while (str != null) {
                RpEntity rpEntity = new RpEntity();
                HashMap<String, Float> apEntities = new HashMap<>();
                String[] eachRpSet = str.split(";");
                for (int i=0;i< eachRpSet.length;i++) {
                    String[] eachAp = eachRpSet[i].split(" ");
                    apEntities.put(eachAp[0],Float.valueOf(eachAp[1]));
                }
                rpEntity.setPoint("A"+count);
                rpEntity.setApEntities(apEntities);
                rpEntities.add(rpEntity);
                str = br.readLine();
                count++;
            }
            br.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return rpEntities;
    }

}
