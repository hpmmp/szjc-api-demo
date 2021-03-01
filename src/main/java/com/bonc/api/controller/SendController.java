package com.bonc.api.controller;

import com.bonc.api.service.CameraListService;
import com.bonc.api.service.CameraQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date 2021/2/1 11:03
 **/
@RestController
@RequestMapping("/camera")
public class SendController {
    @Autowired
    CameraListService cameraListService;
    @Autowired
    CameraQueryService cameraQueryService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SendController.class);

    @GetMapping("/queue_length_det")
    public boolean queueLength(){
        try{
            cameraListService.queueLength();
            return true;
        }catch (Exception e){
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    @GetMapping("/crowd_density_det")
    public boolean crowdDensityDet(){
        try{
            cameraListService.crowdDensityDet();
            return true;
        }catch (Exception e){
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    @GetMapping("/queue_length_det_query")
    public boolean queueLengthDetQuery(){
        try{
            cameraQueryService.queueLengthDetQuery();
            return true;
        }catch (Exception e){
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    @GetMapping("/crowd_density_det_query")
    public boolean crowdDensityDetQuery(){
        try{
            cameraQueryService.crowdDensityDetQuery();
            return true;
        }catch (Exception e){
            LOGGER.info(e.getMessage());
            return false;
        }
    }

}
