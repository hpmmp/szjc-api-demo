package com.bonc.api.service;

import com.bonc.api.entity.cameralist.CameraInfo;
import com.bonc.api.entity.cameraquery.CameraQuery;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import java.util.List;

/**
 * 2020-10-26
 * create by zcs
 */
@Component
public class CopyManagerService {
    @Value("${spring.datasource.ds3.jdbc-url}")
    private String datasourceUrl;
    @Value("${spring.datasource.ds3.username}")
    private String username;
    @Value("${spring.datasource.ds3.password}")
    private String password;
    @Value("${spring.datasource.ds3.driver-class-name}")
    private String driverClass;

    public void insertData(List<?> list,String table){
        String url = new String(datasourceUrl); //数据库URL
        String user = new String(username);            //LibrA用户名
        String pass = new String(password);       //LibrA密码
        String tablename = new String("dm."+table); //定义表信息
        String delimiter = new String("|");              //定义分隔符
        String encoding = new String("UTF8");            //定义字符集
        String driver = new String(driverClass);
        StringBuffer buffer = null;

        switch (table){
            case "dm_dyn_camera_list" :
                buffer = cameraList(list,delimiter);break;
            case "dm_dyn_camera_query" :
                buffer = cameraQuery(list,delimiter);break;
        }
        try {
            //建立目标数据库连接
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, pass);
            BaseConnection baseConn = (BaseConnection) conn;
            baseConn.setAutoCommit(false);
            //初始化表信息
            String sql = "Copy " + tablename + " from STDIN DELIMITER '" + delimiter + "' ENCODING '" + encoding + "'";
            //提交缓存buffer中的数据
            CopyManager cp = new CopyManager(baseConn);
            StringReader reader = new StringReader(buffer.toString());
            cp.copyIn(sql, reader);
            baseConn.commit();
            reader.close();
            baseConn.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

    }

    private StringBuffer cameraList(List<?> list,String delimiter){
        StringBuffer buffer = new StringBuffer();       //定义存放格式化数据的缓存
        for (Object o : list) {
            CameraInfo cameraInfo = (CameraInfo)o;
            buffer.append(
                    (cameraInfo.getCameraID()== null?"":cameraInfo.getCameraID()) + delimiter
                            +(cameraInfo.getAreaId()== null?"":cameraInfo.getAreaId()) + delimiter
                            +(cameraInfo.getAreaName() == null?"":cameraInfo.getAreaName()) + delimiter
                            +(cameraInfo.getDeviceCode()== null?"":cameraInfo.getDeviceCode()) + delimiter
                            +(cameraInfo.getDeviceType()== null?"":cameraInfo.getDeviceType()) + delimiter
                            +(cameraInfo.getDeviceName()== null?"":cameraInfo.getDeviceName()) + delimiter
                            +(cameraInfo.getStatus()== null?"":cameraInfo.getStatus()) + delimiter
                            +(cameraInfo.getRemark()== null?"":cameraInfo.getRemark()) + delimiter
                            +(cameraInfo.getIp()== null?"":cameraInfo.getIp()) + delimiter
                            +(cameraInfo.getPort()== null?"":cameraInfo.getPort())+delimiter
                            +(cameraInfo.getAccessUser()== null?"":cameraInfo.getAccessUser())+delimiter
                            +(cameraInfo.getAccessPwd()== null?"":cameraInfo.getAccessPwd())+delimiter
                            +(cameraInfo.getAddr()== null?"":cameraInfo.getAddr())+delimiter
                            +(cameraInfo.getCaptureServerId()== null?"":cameraInfo.getCaptureServerId())+delimiter
                            +(cameraInfo.getCameraType()== null?"":cameraInfo.getCameraType())+delimiter
                            +(cameraInfo.getCameraRtspSuffix()== null?"":cameraInfo.getCameraRtspSuffix())+delimiter
                            +(cameraInfo.getCameraRtsp()== null?"":cameraInfo.getCameraRtsp())+delimiter
                            +(cameraInfo.getCameraServerType()== null?"":cameraInfo.getCameraServerType())+delimiter
                            +(cameraInfo.getUpdatedTime()== null?"":cameraInfo.getUpdatedTime())+delimiter
                            +(cameraInfo.getUpdatedUserid()== null?"":cameraInfo.getUpdatedUserid())+delimiter
                            +(cameraInfo.getCreatedTime()== null?"":cameraInfo.getCreatedTime())+delimiter
                            +(cameraInfo.getCreatedUserid()== null?"":cameraInfo.getCreatedUserid())+delimiter
                            +(cameraInfo.getFlag()== null?"":cameraInfo.getFlag())+delimiter
                            +new Date()
                            + "\n");
        }
        return buffer;
    }

    private StringBuffer cameraQuery(List<?> list,String delimiter){
        StringBuffer buffer = new StringBuffer();       //定义存放格式化数据的缓存
        for (Object o : list) {
            CameraQuery cameraQuery = (CameraQuery)o;
            buffer.append(
                    (cameraQuery.getCameraID()== null?"":cameraQuery.getCameraID()) + delimiter
                            +(cameraQuery.getCameraName()== null?"":cameraQuery.getCameraName()) + delimiter
                            +(cameraQuery.getHeadCount() == null?"":cameraQuery.getHeadCount()) + delimiter
                            +(cameraQuery.getQueryTime()== null?"":cameraQuery.getQueryTime()) + delimiter
                            +(cameraQuery.getFlag()== null?"":cameraQuery.getFlag())+delimiter
                            +new Date()
                            + "\n");
        }
        return buffer;
    }
}
