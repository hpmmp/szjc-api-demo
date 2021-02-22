package com.bonc.api.entity.cameraquery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @date 2021/2/4 16:15
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CameraQuery {
    private String cameraID;
    private String cameraName;
    private Integer headCount;
    private Long queryTime;
    private String flag;
}
