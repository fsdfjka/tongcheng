package org.example.springinit.controller;

import org.example.springinit.DTO.AdminLoginDTO;
import org.example.springinit.VO.AdminLoginVO;
import org.example.springinit.service.AdminService;
import org.example.springinit.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 平台管理员控制器
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 平台管理员登录接口
     */
    @PostMapping("/login")
    public Result<AdminLoginVO> login(@Validated @RequestBody AdminLoginDTO loginDTO) {
        try {
            AdminLoginVO loginVO = adminService.login(loginDTO);
            return Result.success("登录成功", loginVO);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}