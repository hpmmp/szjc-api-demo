package com.bonc.api.entity.cameralist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @date 2021/2/2 16:00
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CameraInfo {
    private String cameraID;
    private String areaId;
    private String areaName;
    private String deviceCode;
    private Integer deviceType;
    private String deviceName;
    private Integer status;
    private String remark;
    private String ip;
    private Integer port;
    private String accessUser;
    private String accessPwd;
    private String addr;
    private String captureServerId;
    private Integer cameraType;
    private String cameraRtspSuffix;
    private String cameraRtsp;
    private String cameraServerType;
    private Long updatedTime;
    private String updatedUserid;
    private Long createdTime;
    private String createdUserid;
    private String flag;
}
