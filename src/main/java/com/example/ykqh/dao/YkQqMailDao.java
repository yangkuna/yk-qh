package com.example.ykqh.dao;

import com.example.ykqh.model.YkQqMail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YkQqMailDao {
    int deleteByPrimaryKey(Integer id);

    int insert(YkQqMail record);

    int insertSelective(YkQqMail record);

    YkQqMail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YkQqMail record);

    int updateByPrimaryKey(YkQqMail record);

    List<YkQqMail> selectAllMail();
}