package org.example.springinit.controller;

import org.example.springinit.DTO.UserLoginDTO;
import org.example.springinit.DTO.UserRegisterDTO;
import org.example.springinit.pojo.User;
import org.example.springinit.VO.UserLoginVO;
import org.example.springinit.service.UserService;
import org.example.springinit.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api/user")
@ResponseBody
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册接口
     */
    @PostMapping("/register")
    public Result<User> register(@Validated @RequestBody UserRegisterDTO registerDTO) {
        try {
            User user = userService.register(registerDTO);
            return Result.success("注册成功", user);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    @PostMapping("/login")
    public Result<UserLoginVO> login(@Validated @RequestBody UserLoginDTO loginDTO) {
        try {
            UserLoginVO loginVO = userService.login(loginDTO);
            return Result.success("登录成功", loginVO);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}