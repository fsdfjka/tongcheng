package org.example.springinit.service;

import org.example.springinit.DTO.UserLoginDTO;
import org.example.springinit.DTO.UserRegisterDTO;
import org.example.springinit.pojo.User;
import org.example.springinit.VO.UserLoginVO;


/**
 * 用户业务层
 */
public interface UserService {
    /**
     * 用户注册
     */
    User register(UserRegisterDTO registerDTO);

    // 登录
    UserLoginVO login(UserLoginDTO loginDTO);
}