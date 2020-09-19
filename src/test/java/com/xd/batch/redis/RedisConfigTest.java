package com.xd.batch.redis;

import com.xd.batch.entity.User;
import com.xd.batch.mapper.test1.UserMapper;
import com.xd.batch.service.Test34Service;
import com.xd.batch.service.TestService;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisConfigTest {

  /*  @Resource
    private RedisCache redisCache;*/

    @Autowired
    private TestService testService;

    @Autowired
    private Test34Service test34Service;

    @Resource(name = "test1SqlSessionFactory")
    private SqlSessionFactory test1SqlSessionFactory;

    @Resource(name = "test1SqlSessionTemplate")
    private SqlSessionTemplate test1SqlSessionTemplate;



   /* @Test
    public void HashOperations() throws Exception{
        User userVo = new User();
        userVo.setUserName("jantent");
        userVo.setPassword("23");
        //redisUtil.hSet("1","2",userVo);
        redisCache.hSet("1","3U",userVo);
        User user = (User) redisCache.hGet("1", "3U");
        System.out.println("============hGet==================");
        System.out.println(user);
        System.out.println("============hmGet==================");
        Map<Object, Object> objectObjectMap = redisCache.hmGet("1");
        objectObjectMap.forEach((key,value)->{
            System.out.println(key + ":" + value);
        });
    }
    @Test
    public void testRedis(){
        User user =  (User) redisCache.get("name");
        System.out.println("name:"+user);
    }
    @Test
    public void add(){
        User user = new User();
        user.setUserName("xiaodai-1");
        user.setPassword("1992");
        user.setAge(22);
        redisCache.set("a-xiaodai", user);
    }

    @Test
    public void del(){
        redisCache.delKey("user");
    }*/


    @Test
    public void doUser() throws Exception {

        System.out.println(testService.doStudentProcess());

        System.out.println(testService.doUserProcess());
    }

    @Test
    public void doTest34() throws Exception {
        System.out.println(test34Service.doPersonProcess());
    }

    @Test
    public void doMybatis() throws Exception {
        Configuration configuration = test1SqlSessionFactory.getConfiguration();
        String sqlId = UserMapper.class.getName() + ".insert";
        MappedStatement mappedStatement = configuration.getMappedStatement(sqlId);
        MappedStatement mappedStatement2 = configuration.getMappedStatement(UserMapper.class.getName() + ".select");
        MappedStatement mappedStatement3 = configuration.getMappedStatement(UserMapper.class.getName() + ".find");
        MappedStatement mappedStatement4 = configuration.getMappedStatement(UserMapper.class.getName() + ".batch");
        MappedStatement mappedStatement5 = configuration.getMappedStatement(UserMapper.class.getName() + ".selectUnionAll");
        BoundSql boundSql = mappedStatement.getBoundSql(null);
        String sql = boundSql.getSql();
        List<User> userList = new ArrayList<>();
        User user = new User("xiaodai");
        user.setPassword("22");
        user.setAge(66);
        User user2 = new User("xd1");
        userList.add(user);
        userList.add(user2);
        //userList.add(user2);
        Map<String,List<User>> map = new HashMap<>();
        map.put("list",userList);
        BoundSql boundSql3 = mappedStatement3.getBoundSql(user);
        BoundSql boundSql4 = mappedStatement4.getBoundSql(map);
        BoundSql boundSql2 = mappedStatement2.getBoundSql(map);
        BoundSql boundSql5 = mappedStatement5.getBoundSql(map);
        String sql5 = boundSql5.getSql();
        System.out.println("sql5:"+sql5);


        sql = boundSql2.getSql();
         String selectSql = "select id,user_name,`age`,`password` from t_user_two t1 join(select ? as u_user_name) t2 on (t1.user_name = t2.u_user_name)";



        List<ParameterMapping> parameterMappings = boundSql2.getParameterMappings();
        SqlSession sqlSession = test1SqlSessionFactory.openSession(true);
        Connection connection = sqlSession.getConnection();
        sql = boundSql4.getSql();
        PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
        int a  =1;
        for (int i = 0; i < parameterMappings.size(); i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            String property = parameterMapping.getProperty();
            ParameterMode mode = parameterMapping.getMode();
            try {
                if(parameterMapping.getJdbcType().equals(JdbcType.INTEGER)){
                    preparedStatement.setObject(i+1,66,parameterMapping.getJdbcType().TYPE_CODE);
                }else{
                    preparedStatement.setObject(i+1,user.getUserName(),parameterMapping.getJdbcType().TYPE_CODE);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        };
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            int id = resultSet.getInt("id");// 获取第一个列的值 编号id
            String userName = resultSet.getString("user_name"); // 获取第二个列的值 图书名称 bookName
            String password = resultSet.getString("password");// 获取第三列的值 图书作者 author
            int age = resultSet.getInt("age");// 获取第四列的值 图书价格 price
            System.out.println(userName + "........." + password + "....." + age + "..................................");
        }
    }

    @Test
    public void testExecuteSql() throws SQLException {
        SqlSession sqlSession = test1SqlSessionFactory.openSession(true);
        Connection connection = sqlSession.getConnection();
        String selectSql = "select id,user_name,`age`,`password` from t_user t1 join(select ? as u_user_name union all select ? as t_user_name) t2 on (t1.user_name = t2.u_user_name)";
        String selectSql2 = "select id,user_name,`age`,`password` from t_user_two t1 where t1.user_name in(?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSql2);
        String[] arr = selectSql.split("\\?");
        preparedStatement.setObject(1,"xiaodai",JdbcType.VARCHAR.TYPE_CODE);
        preparedStatement.setObject(2,"xiaodai5",JdbcType.VARCHAR.TYPE_CODE);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            int id = resultSet.getInt("id");// 获取第一个列的值 编号id
            String userName = resultSet.getString("user_name"); // 获取第二个列的值 图书名称 bookName
            String password = resultSet.getString("password");// 获取第三列的值 图书作者 author
            int age = resultSet.getInt("age");// 获取第四列的值 图书价格 price
            System.out.println(userName + "........." + password + "....." + age + "..................................");
        }

    }

    @Test
    public void testList() throws ClassNotFoundException {
        SqlSession sqlSession = test1SqlSessionFactory.openSession(true);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setPassword("xiaodai123456");
        user.setAge(23);
        user.setUserName("xiaodaixiaodai");
        mapper.insert(user);

        List<User> users = mapper.find(user);

        System.out.println();

    }

}
