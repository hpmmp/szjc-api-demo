package com.bonc.api;

import com.bonc.api.service.CameraListService;
import com.bonc.api.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiApplicationTests {

    @Autowired
    CameraListService cameraListService;
    @Autowired
    RedisUtil redisUtil;

    @Test
    public void contextLoads() {
        System.out.println(System.currentTimeMillis());
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE,5);
        System.out.println(instance.getTime().getTime()-1);
    }

    @Test
    public void tesst3(){
        System.out.println(redisUtil.getString("crowd_density_det_query"));
    }
}
