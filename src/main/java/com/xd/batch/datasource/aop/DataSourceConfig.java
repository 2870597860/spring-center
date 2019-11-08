package com.xd.batch.datasource.aop;

import com.xd.batch.datasource.DefinePageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
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
import java.util.HashMap;
import java.util.Map;

@Configuration
@MapperScan(basePackages = "com.xd.batch.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {

    @Bean("test3DataTranManager")
    public PlatformTransactionManager transactionManager3(@Qualifier("test3DataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("test3TransactionTemplate")
    @Qualifier("test3TransactionTemplate")
    public TransactionTemplate transactionTemplate3(@Qualifier("test3DataTranManager") PlatformTransactionManager platformTransactionManager){
        return new TransactionTemplate(platformTransactionManager);
    }

    @Bean("test4DataTranManager")
    public PlatformTransactionManager transactionManager4(@Qualifier("test4DataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("test4TransactionTemplate")
    @Qualifier("test4TransactionTemplate")
    public TransactionTemplate transactionTemplate4(@Qualifier("test4DataTranManager") PlatformTransactionManager platformTransactionManager){
        return new TransactionTemplate(platformTransactionManager);
    }

    // 将这个对象放入Spring容器中
    @Bean(name = "test3DataSource")
    // 读取application.properties中的配置参数映射成为一个对象
    @ConfigurationProperties(prefix = "spring.datasource.test3")
    public DataSource getDateSource3() {
        return DataSourceBuilder.create().build();
    }

    // 将这个对象放入Spring容器中
    @Bean(name = "test4DataSource")
    // 读取application.properties中的配置参数映射成为一个对象
    @ConfigurationProperties(prefix = "spring.datasource.test4")
    public DataSource getDateSource4() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "dynamicDataSource")
    public DynamicDataSource DataSource(@Qualifier("test3DataSource") DataSource test3DataSource,
                                        @Qualifier("test4DataSource") DataSource test4DataSource) {
        Map<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DataSourceType.DataBaseType.TEST3, test3DataSource);
        targetDataSource.put(DataSourceType.DataBaseType.TEST4, test4DataSource);
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSource);
        dataSource.setDefaultTargetDataSource(test3DataSource);
        return dataSource;
    }

    @Bean(name = "sqlSessionFactory")
    // @Qualifier表示查找Spring容器中名字为test1DataSource的对象
    public SqlSessionFactory test1SqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);
        // 设置mybatis的xml所在位置
        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mapper/aop/*.xml"));
        bean.setPlugins(new Interceptor[]{DefinePageInterceptor.buildPageInterceptor()});
        return bean.getObject();
    }

}
