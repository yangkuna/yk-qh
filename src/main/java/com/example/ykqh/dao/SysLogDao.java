package com.example.ykqh.dao;

import com.example.ykqh.model.YkLog;
import org.springframework.stereotype.Repository;

@Repository
public interface SysLogDao {
    int deleteByPrimaryKey(Integer logId);

    int insert(YkLog record);

    int insertSelective(YkLog record);

    YkLog selectByPrimaryKey(Integer logId);

    int updateByPrimaryKeySelective(YkLog record);

    int updateByPrimaryKey(YkLog record);
}