package com.example.ykqh.dao;

import com.example.ykqh.model.YkRole;
import org.springframework.stereotype.Repository;

/**
 * @author yk
 * @describe 角色dao
 */
@Repository
public interface RoleDao {
    /** 根据角色id删除数据
     * @param roleId 角色id
     * @return 无
     */
    int deleteByPrimaryKey(Integer roleId);
    /** 插入角色数据
     * @param record 角色信息
     * @return 无
     */
    int insert(YkRole record);
    /** 非空角色插入数据
     * @param record 角色信息
     * @return 无
     */
    int insertSelective(YkRole record);
    /** 根据主键id查询角色数据
     * @param roleId 角色id
     * @return 无
     */
    YkRole selectByPrimaryKey(Integer roleId);
    /** 更新角色非空字段数据
     * @param record 角色信息
     * @return 无
     */
    int updateByPrimaryKeySelective(YkRole record);
    /** 更新角色全部数据
     * @param record 角色信息
     * @return 无
     */
    int updateByPrimaryKey(YkRole record);
}