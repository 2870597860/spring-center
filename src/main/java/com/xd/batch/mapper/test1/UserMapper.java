package com.xd.batch.mapper.test1;

import com.xd.batch.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    int insert(Object obj);
    List<User> find(Object obj);
}
