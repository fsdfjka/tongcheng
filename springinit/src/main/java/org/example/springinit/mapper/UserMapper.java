package org.example.springinit.mapper;

import org.example.springinit.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户数据访问层
 */
@Mapper
public interface UserMapper {
    /**
     * 根据手机号查询用户（校验唯一性）
     */
    User selectByPhone(@Param("phone") String phone);

    /**
     * 新增用户（注册）
     */
    int insert(User user);
    /**
     * 按账号（手机号/用户名）查询用户（用于管理员登录）
     */
    User selectByAccount(@Param("account") String account);
}