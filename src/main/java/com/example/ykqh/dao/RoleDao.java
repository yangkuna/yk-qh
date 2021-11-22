package com.example.ykqh.dao;

import com.example.ykqh.model.YkRole;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao {
    int deleteByPrimaryKey(Integer roleId);

    int insert(YkRole record);

    int insertSelective(YkRole record);

    YkRole selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(YkRole record);

    int updateByPrimaryKey(YkRole record);
}