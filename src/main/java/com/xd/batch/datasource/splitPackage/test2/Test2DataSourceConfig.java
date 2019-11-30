package com.xd.batch.datasource.splitPackage.test2;

import com.xd.batch.datasource.DefinePageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
// 配置mybatis的接口类放的地方
@MapperScan(basePackages = "com.xd.batch.mapper.test2", sqlSessionFactoryRef = "test2SqlSessionFactory")
public class Test2DataSourceConfig {

    @Bean("test2DataTranManager")
    public PlatformTransactionManager transactionManager(@Qualifier("test2DataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("test2TransactionTemplate")
    @Qualifier("test2TransactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("test2DataTranManager") PlatformTransactionManager platformTransactionManager){
        return new TransactionTemplate(platformTransactionManager);
    }

    // 将这个对象放入Spring容器中
    @Bean(name = "test2DataSource")
    // 读取application.properties中的配置参数映射成为一个对象
    @ConfigurationProperties(prefix = "spring.datasource.test2")
    public DataSource getDateSource1() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "test2SqlSessionFactory")
    // @Qualifier表示查找Spring容器中名字为test2DataSource的对象
    public SqlSessionFactory test2SqlSessionFactory(@Qualifier("test2DataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        // 设置mybatis的xml所在位置
        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mapper/test2/*.xml"));
        bean.setPlugins(new Interceptor[]{DefinePageInterceptor.buildPageInterceptor()});
        return bean.getObject();
    }
    @Bean("test2SqlSessionTemplate")
    public SqlSessionTemplate test2sqlsessiontemplate(
            @Qualifier("test2SqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }
}
