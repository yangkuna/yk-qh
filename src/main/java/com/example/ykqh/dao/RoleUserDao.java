package com.example.ykqh.dao;

import com.example.ykqh.model.RoleUser;

import org.springframework.stereotype.Repository;

/**
 * @author yk
 */
@Repository
public interface RoleUserDao {
    int deleteByPrimaryKey(RoleUser key);

    int insert(RoleUser record);

    int insertSelective(RoleUser record);

    RoleUser selectByPrimaryKey(RoleUser key);

    int updateByPrimaryKeySelective(RoleUser record);

    int updateByPrimaryKey(RoleUser record);
}