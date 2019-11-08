package com.xd.batch.datasource.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.isNull;

public class DataSourceType {
    private static final Logger LOG = LoggerFactory.getLogger(DataSourceType.class);
    public enum DataBaseType{
        TEST3,TEST4
    }
    private static final ThreadLocal<DataBaseType> dataSourceType = new ThreadLocal<>();
    public static void setDataBaseType(DataBaseType dataBaseType){
        if(isNull(dataBaseType)){
            throw new NullPointerException("dataSourceType is null");
        }
        LOG.info("[将当前数据源修改为]:"+dataBaseType);
        dataSourceType.set(dataBaseType);
    }
    public static DataBaseType getDateBaseType(){
        DataBaseType dataBaseType = isNull(dataSourceType.get())?DataBaseType.TEST3:dataSourceType.get();
        LOG.info("[获取当前的数据源类型为]："+dataBaseType);
        return dataBaseType;
    }
    public static void clearDataBaseType(){
        dataSourceType.remove();
    }
}
