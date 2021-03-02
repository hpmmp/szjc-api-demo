package com.bonc.api.service;

import com.bonc.api.entity.cameralist.CameraInfo;
import com.bonc.api.entity.cameraquery.CameraQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @date 2021/3/2 10:51
 **/
@Service
public class DemoData {

    public List<CameraInfo>  getCameraInfo(String apiName){
        //返回视频设备demo列表 10个
        List<CameraInfo> list = new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            CameraInfo cameraInfo = new CameraInfo("camera_id", "000001", "苏研所", "06418463391314922129",
                    4, "172_21_83_151", 1, "", "192.168.103.25", 8000, null, null, "",
                    "99", null, null, "rtsp://admin:123456@172.21.86.51:25042/live/06418463391314922129/1",
                    "4", null, "1000", 1564112043409L, "2000", null);
            cameraInfo.setFlag(apiName);
            list.add(cameraInfo);
        }
        return list;
    }

    public List<CameraQuery> getCameraQuery(String apiName,int headCount){
        Calendar instance = Calendar.getInstance();
        List<CameraQuery> list = new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            CameraQuery cameraQuery = new CameraQuery("camera_id", "操作部进港仓库", (int) (Math.random() * 10), instance.getTime().getTime(), null);
            if(cameraQuery.getHeadCount()>=headCount){
                cameraQuery.setFlag(apiName);
                list.add(cameraQuery);
            }
        }
        return list;
    }
}
