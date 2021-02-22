package com.bonc.api.service;

import com.alibaba.fastjson.JSONObject;
import com.bonc.api.entity.cameralist.CameraInfo;
import com.bonc.api.entity.cameralist.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * @date 2021/2/4 10:13
 **/
@Service
public class CameraListService {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CopyManagerService copyManagerService;
    @Value("${test1.url}")
    public String test1Url;


    public void queueLength(){
        insertCameraList("queueLength");
    }

    public void crowdDensityDet(){
        insertCameraList("crowdDensityDet");
    }

    public void insertCameraList(String apiName){
        int totalRow;
        //获取总记录数
        Result rowResult= getCameraInfo(1);
        if(rowResult!=null&&rowResult.getData()!=null&&rowResult.getData().getTotalRows()!=null){
            totalRow=rowResult.getData().getTotalRows();
            Result result = getCameraInfo(totalRow);
            if(result!=null&&result.getData()!=null&&result.getData().getDatas()!=null&&result.getData().getDatas().length>0){
                List<CameraInfo> cameraInfos = Arrays.asList(result.getData().getDatas());
                for (CameraInfo info : cameraInfos) {
                    info.setFlag(apiName);
                }
                copyManagerService.insertData(cameraInfos,"dm_dyn_camera_list");
            }
        }
    }

    public Result getCameraInfo(Integer pageSize) {
        String url=test1Url;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("pageSize",pageSize);
        HttpEntity<JSONObject> request = new HttpEntity<>(jsonObj, headers);
        Result result = restTemplate.postForObject(url, request, Result.class);
        return result;
    }
}
