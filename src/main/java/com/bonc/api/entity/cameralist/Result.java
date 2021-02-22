package com.bonc.api.entity.cameralist;

import lombok.Data;

/**
 * @date 2021/2/2 17:10
 **/
@Data
public class Result {
    private String status;
    private String message;
    private ErrorObj[] error;
    private DataObj data;
}
