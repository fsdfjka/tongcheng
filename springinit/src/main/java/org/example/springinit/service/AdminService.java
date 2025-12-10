package org.example.springinit.service;

import org.example.springinit.DTO.AdminLoginDTO;
import org.example.springinit.VO.AdminLoginVO;

/**
 * 平台管理员业务接口
 */
public interface AdminService {
    /**
     * 管理员登录
     */
    AdminLoginVO login(AdminLoginDTO loginDTO);
}