package com.xd.batch.mapper.aop.test4;

import org.springframework.stereotype.Repository;

@Repository
public interface PersonMapper {
    int insert(Object obj);
}
