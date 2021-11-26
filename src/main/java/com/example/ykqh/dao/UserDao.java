package com.example.ykqh.dao;

import com.example.ykqh.model.YkUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    int deleteByPrimaryKey(@Param("userId") Integer userId);

    int insert(YkUser record);

    int insertSelective(YkUser record);

    YkUser selectByPrimaryKey(@Param("userPhone") String userPhone, @Param("pwd") String pwd);

    int updateByPrimaryKeySelective(YkUser record);

    int updateByPrimaryKeyWithBLOBs(YkUser record);

    int updateByPrimaryKey(YkUser record);

    List<YkUser> getAllUserInfo();

    YkUser getUserInfoById(@Param("userId") Integer userId);
}