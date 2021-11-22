package com.example.ykqh.dao;

import com.example.ykqh.model.YkUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(YkUser record);

    int insertSelective(YkUser record);

    YkUser selectByPrimaryKey(@Param("userCode") String userCode, @Param("pwd") String pwd);

    int updateByPrimaryKeySelective(YkUser record);

    int updateByPrimaryKeyWithBLOBs(YkUser record);

    int updateByPrimaryKey(YkUser record);

    List<YkUser> getAllUserInfo();

    YkUser getUserInfoById(@Param("id") Integer id);
}