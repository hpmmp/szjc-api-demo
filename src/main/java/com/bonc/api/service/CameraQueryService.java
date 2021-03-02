package com.bonc.api.service;

import com.alibaba.fastjson.JSONObject;
import com.bonc.api.entity.cameraquery.CameraQuery;
import com.bonc.api.entity.cameraquery.Result;
import com.bonc.api.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @date 2021/2/5 11:23
 *           //1获取redis时间戳值
 *         //2判断是否为空
 *         //2.1 为空更新时间戳为5分钟前，递归调用
 *         // 2.2 不为空
 *         //一（获取当前时间戳，传入时间范围，【redis时间戳，当前时间戳】）
 *         //二 （获取到的值入库）判断是否大于阈值
 *         //三 （更新redis = 当前时间戳+1）
 **/
@Service
public class CameraQueryService {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CopyManagerService copyManagerService;
    @Autowired
    RedisUtil redisUtil;
    @Value("${test2.url}")
    public String test2Url;


    @Scheduled(cron = "0 0/2 * * * ?")
    public void queueLengthDetQuery() {
        insertCameraQuery("queue_length_det_query","dm_dyn_camera_query", 2);
    }

    @Scheduled(cron = "0 0/2 * * * ?")
    public void crowdDensityDetQuery() {
        insertCameraQuery("crowd_density_det_query","dm_dyn_camera_query", 2);
    }

    private void insertCameraQuery(String redisName,String tableName,int headCount) {
        String time = redisUtil.getString(redisName);
        if(StringUtils.isEmpty(time)){
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.MINUTE,-5);
            if (!redisUtil.exists(redisName)) {
                redisUtil.setString(redisName,String.valueOf(instance.getTimeInMillis()), 300);//设置过期时间为5分钟
            }
            insertCameraQuery(redisName,tableName,headCount);
        }else{
            Calendar instance = Calendar.getInstance();
            long currentTime = instance.getTimeInMillis();
            System.out.println(redisName+"--"+time+"--"+new Date(Long.parseLong(time)));
            System.out.println("currentTime--"+currentTime+"--"+new Date(currentTime));
            List<CameraQuery> list = queryCamera(Long.parseLong(time), currentTime,redisName,headCount);
            copyManagerService.insertData(list,tableName);
            redisUtil.setString(redisName,String.valueOf(currentTime+1), 300);//设置过期时间为5分钟
        }
    }

    private List<CameraQuery> queryCamera(long start,long end,String RedisName,int head){
        String url=test2Url;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("pageSize",10000);//设置1w保证查询信息覆盖时间段
        jsonObj.put("startTime",start);
        jsonObj.put("endTime",end);
        HttpEntity<JSONObject> request = new HttpEntity<>(jsonObj, headers);
        Result result = restTemplate.postForObject(url, request, Result.class);
        List<CameraQuery> list = new ArrayList<>();
        if(result!=null&&result.getData()!=null&&result.getData().getDatas()!=null&&result.getData().getDatas().length>0){
            List<CameraQuery> cameraQueries = Arrays.asList(result.getData().getDatas());
            for (CameraQuery cameraQuery : cameraQueries) {
                if(cameraQuery.getHeadCount()>=head){//阈值设置为2
                    cameraQuery.setFlag(RedisName);
                    list.add(cameraQuery);
                }
            }
        }
        return list;
    }

}
