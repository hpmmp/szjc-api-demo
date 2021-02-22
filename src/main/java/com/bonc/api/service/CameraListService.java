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
    @Value("${camera.queuelengthdet.list.url}")
    public String queueLengthDetListUrl;
    @Value("${camera.crowddensitydet.list.url}")
    public String crowdDensityDetListUrl;
    @Value("${api.appkey.name}")
    public String appkeyName;
    @Value("${api.appkey.value}")
    public String appkeyValue;
    @Value("${api.id.name}")
    public String idName;
    @Value("${api.id.value}")
    public String idValue;


    public void queueLength(){
        insertCameraList("queueLength",queueLengthDetListUrl);
    }

    public void crowdDensityDet(){
        insertCameraList("crowdDensityDet",crowdDensityDetListUrl);
    }

    private void insertCameraList(String apiName,String url){
        int totalRow;
        //获取总记录数
        Result rowResult= getCameraInfo(1,url);
        if(rowResult!=null&&rowResult.getData()!=null&&rowResult.getData().getTotalRows()!=null){
            totalRow=rowResult.getData().getTotalRows();
            Result result = getCameraInfo(totalRow,url);
            if(result!=null&&result.getData()!=null&&result.getData().getDatas()!=null&&result.getData().getDatas().length>0){
                List<CameraInfo> cameraInfos = Arrays.asList(result.getData().getDatas());
                for (CameraInfo info : cameraInfos) {
                    info.setFlag(apiName);
                }
                copyManagerService.insertData(cameraInfos,"dm_dyn_camera_list");
            }
        }
    }

    private Result getCameraInfo(Integer pageSize,String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(appkeyName,appkeyValue);
        headers.add(idName,idValue);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("pageSize",pageSize);
        HttpEntity<JSONObject> request = new HttpEntity<>(jsonObj, headers);
        return restTemplate.postForObject(url, request, Result.class);
    }
}
