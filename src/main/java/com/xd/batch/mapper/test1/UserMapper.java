package com.xd.batch.mapper.test1;

import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    int insert(Object obj);
    void select();
}
