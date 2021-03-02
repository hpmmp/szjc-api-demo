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

    @Value("${camera.queuelengthdet.query.url}")
    public String queueLengthDetQueryUrl;
    @Value("${camera.crowddensitydet.query.url}")
    public String crowdDensityDetQueryUrl;
    @Value("${camera.queuelengthdet.redis.key}")
    public String queueLengthDetRedisKey;
    @Value("${camera.crowddensitydet.redis.key}")
    public String crowdDensityDetRedisKey;
    @Value("${camera.queuelengthdet.headcount}")
    public String queueHeadCount;
    @Value("${camera.crowddensitydet.headcount}")
    public String crowdHeadCount;

    @Value("${api.appkey.name}")
    public String appkeyName;
    @Value("${api.appkey.value}")
    public String appkeyValue;
    @Value("${api.id.name}")
    public String idName;
    @Value("${api.id.value}")
    public String idValue;


    @Scheduled(cron = "0 0/2 * * * ?")
    public void queueLengthDetQuery() {
        insertCameraQuery(queueLengthDetQueryUrl,queueLengthDetRedisKey, Integer.parseInt(queueHeadCount));
    }

    @Scheduled(cron = "0 0/2 * * * ?")
    public void crowdDensityDetQuery() {
        insertCameraQuery(crowdDensityDetQueryUrl,crowdDensityDetRedisKey, Integer.parseInt(crowdHeadCount));
    }

    private void insertCameraQuery(String url,String redisName,int headCount) {
        String time = redisUtil.getString(redisName);
        if(StringUtils.isEmpty(time)){
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.MINUTE,-5);
            if (!redisUtil.exists(redisName)) {
                redisUtil.setString(redisName,String.valueOf(instance.getTimeInMillis()), 300);//设置过期时间为5分钟
            }
            insertCameraQuery(url,redisName,headCount);
        }else{
            Calendar instance = Calendar.getInstance();
            long currentTime = instance.getTimeInMillis();
            List<CameraQuery> list = queryCamera(url,Long.parseLong(time), currentTime,redisName,headCount);
            copyManagerService.insertData(list,"dm_dyn_camera_query");
            redisUtil.setString(redisName,String.valueOf(currentTime+1), 300);//设置过期时间为5分钟
        }
    }

    private List<CameraQuery> queryCamera(String url,long start,long end,String RedisName,int headCount){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(appkeyName,appkeyValue);
        headers.add(idName,idValue);
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
                if(cameraQuery.getHeadCount()>=headCount){//与阈值比较
                    cameraQuery.setFlag(RedisName);
                    list.add(cameraQuery);
                }
            }
        }
        return list;
    }

}
