package com.bonc.api.entity.cameralist;

import lombok.Data;

/**
 * @date 2021/2/2 17:13
 **/
@Data
public class DataObj {
    private Integer pageSize;
    private Integer currentPage;
    private Integer totalPages;
    private Integer totalRows;
    private Integer minRowNumber;
    private Integer maxRowNumber;
    private CameraInfo[] datas;
}
