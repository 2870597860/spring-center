package com.xd.batch.datasource.splitPackage.test1;

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
@MapperScan(basePackages = "com.xd.batch.mapper.splitPackage.test1", sqlSessionFactoryRef = "test1SqlSessionFactory")
public class Test1DataSourceConfig {

    @Bean("test1DataTranManager")
    public PlatformTransactionManager transactionManager(@Qualifier("test1DataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("test1TransactionTemplate")
    @Qualifier("test1TransactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("test1DataTranManager") PlatformTransactionManager platformTransactionManager){
        return new TransactionTemplate(platformTransactionManager);
    }

    // 将这个对象放入Spring容器中
    @Bean(name = "test1DataSource")
    // 读取application.properties中的配置参数映射成为一个对象
    @ConfigurationProperties(prefix = "spring.datasource.test1")
    public DataSource getDateSource1() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "test1SqlSessionFactory")
    // @Qualifier表示查找Spring容器中名字为test1DataSource的对象
    public SqlSessionFactory test1SqlSessionFactory(@Qualifier("test1DataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        // 设置mybatis的xml所在位置
        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mapper/splitPackage/test1/*.xml"));
        bean.setPlugins(new Interceptor[]{DefinePageInterceptor.buildPageInterceptor()});
        return bean.getObject();
    }
    @Bean("test1SqlSessionTemplate")
    public SqlSessionTemplate test1sqlsessiontemplate(
            @Qualifier("test1SqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }
}
