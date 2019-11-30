package com.xd.batch.datasource.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAop {
    private static final Logger LOG = LoggerFactory.getLogger(DataSourceAop.class);

    @Before(value = "execution(* com.xd.batch.mapper.aop.test3..*(..))")
    public void setTest3DataSource(JoinPoint joinPoint){
        LOG.info("设置为test3数据源");
        DataSourceType.setDataBaseType(DataSourceType.DataBaseType.TEST3);
    }

    @Before(value = "execution(* com.xd.batch.mapper.aop.test4..*(..))")
    public void setTest4DataSource(){
        LOG.info("设置为test4数据源");
        DataSourceType.setDataBaseType(DataSourceType.DataBaseType.TEST4);
    }
}
