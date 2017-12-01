package com.example.fnweb.Service;

import com.example.fnweb.Entity.BayesArgsEntity;
import com.example.fnweb.Entity.PointLocEntity;
import com.example.fnweb.Entity.RssiEntity;
import com.example.fnweb.Mapper.BayesMapper;
import com.example.fnweb.Mapper.DeviceMapper;
import com.example.fnweb.Mapper.PointLocMapper;
import com.example.fnweb.Mapper.RssiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import static java.lang.Math.E;

/**
 * Created by ACER on 2017/11/22.
 */
@Service
public class NaiveBayesService {

    @Autowired
    private BayesMapper bayesMapper;

    @Autowired
    private PointLocMapper pointLocMapper;

    @Autowired
    private RssiMapper rssiMapper;

    private int apAmount = 9;

    private int k = 5;

    //get all the rssi of each ap in each point and calculate the average and variance
    @Transactional
    public boolean getArgsFromData(){
        List<String> allPoints = pointLocMapper.getAllPointName();
        for (String str : allPoints) {
            for (int i = 1; i <= apAmount; i++) {
                String apName = "ap"+i;
                List<Double> eachApData = rssiMapper.getEachApRssiByPointName(apName,str);
                if (!computeAndInsertGaussArgs(eachApData,str,apName)) return false;
            }
        }
        return true;
    }

    private boolean computeAndInsertGaussArgs(List<Double> eachApData,String pointName, String apName) {
        double sum = 0.0, variance = 0.0;
        for (Double eachResult : eachApData) sum += eachResult;
        double average = sum/eachApData.size();

        for (Double eachResult : eachApData) variance += Math.pow((eachResult - average),2);
        variance = variance/eachApData.size();

        String avgName = apName+"_average";
        String varName = apName+"_variance";

        //insert a point if store the args for the first time
        //update the point info if the point has existed
        if (bayesMapper.hasPoint(pointName)){
            if(!bayesMapper.updateBayesArgs(avgName,varName,pointName,average,variance)) return false;
        }else{
            if (!bayesMapper.insertBayesArgs(avgName,varName,pointName,average,variance)) return false;
        }
        return true;
    }

    public  RssiEntity getLocByBayes(RssiEntity rssiEntity){
        RssiEntity resultRssiEntity = new RssiEntity();

        //initialize the input data
        Float[] eachApRssiSrc = new Float[apAmount];
        eachApRssiSrc[0] = rssiEntity.getAp1();
        eachApRssiSrc[1] = rssiEntity.getAp2();
        eachApRssiSrc[2] = rssiEntity.getAp3();
        eachApRssiSrc[3] = rssiEntity.getAp4();
        eachApRssiSrc[4] = rssiEntity.getAp5();
        eachApRssiSrc[5] = rssiEntity.getAp6();
        eachApRssiSrc[6] = rssiEntity.getAp7();
        eachApRssiSrc[7] = rssiEntity.getAp8();
        eachApRssiSrc[8] = rssiEntity.getAp9();

        //get ratio and location info of each point and add up for the result
        PointLocEntity[] maxKEntities = getKPointsWithHighestProb(eachApRssiSrc);
        double sum = 0,x=0,y=0;
        for (int i = 0; i < maxKEntities.length; i++){
            sum += maxKEntities[i].getBayesResult();
        }
        for (int i = 0; i < maxKEntities.length; i++){
            double ratio = maxKEntities[i].getBayesResult()/sum;
            PointLocEntity locEntity = pointLocMapper.getPointLocInfoByEntity(maxKEntities[i]);
            x += ratio * locEntity.getX();
            y += ratio * locEntity.getY();
        }

        //convert the format of location info according to how it store into database in knnService
        double result_x = x/Math.pow(10,7) + 12735839;
        double result_y = y/Math.pow(10,7) + 3569534;
        resultRssiEntity.setX(result_x);
        resultRssiEntity.setY(result_y);
        return resultRssiEntity;
    }

    private PointLocEntity[] getKPointsWithHighestProb(Float[] eachApRssiSrc){

        //use the way of priority queue, which could be compared to the knn
        Comparator<PointLocEntity> cmp = new Comparator<PointLocEntity>() {
            @Override
            public int compare(PointLocEntity o1, PointLocEntity o2) {
                return o2.getBayesResult()>o1.getBayesResult()?1:-1;
            }
        };

        Queue<PointLocEntity> maxKPoints = new PriorityQueue<>(cmp);
        PointLocEntity[] pointLocEntities = new PointLocEntity[k];

        //calculate the probability of each candidate point, pick the max k
        List<String> allPointNames = bayesMapper.getAllPointName();
        for (String pointName : allPointNames) {
            PointLocEntity candidateLocEntity = new PointLocEntity();
            double eachPointProb=1.0;

            //put the online data into Gauss formula with Gauss index of each ap
            for (int i = 1; i <= apAmount; i++) {
                String avgName = "ap"+i+"_average";
                String varName = "ap"+i+"_variance";
                BayesArgsEntity eachAp = bayesMapper.getEachApArgs(avgName,varName,pointName);
                if (eachAp.getApNameVar() == 0)
                    eachAp.setApNameVar(0.000001);
                double thisApProb = 1/Math.sqrt(2*Math.PI*eachAp.getApNameVar())*Math.pow(E,-Math.pow(eachApRssiSrc[i-1] - eachAp.getApNameAvg(),2)/2*eachAp.getApNameVar());
                eachPointProb *= thisApProb;
            }
            candidateLocEntity.setPoint_name(pointName);
            candidateLocEntity.setBayesResult(eachPointProb);
            maxKPoints.offer(candidateLocEntity);
        }
        for (int i = 0; i < k; i++) pointLocEntities[i] = maxKPoints.poll();
        return pointLocEntities;
    }


}
