package org.example.springinit.service.impl;

import org.example.springinit.DTO.AdminLoginDTO;
import org.example.springinit.VO.AdminLoginVO;
import org.example.springinit.mapper.UserMapper;
import org.example.springinit.pojo.User;
import org.example.springinit.service.AdminService;
import org.example.springinit.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 平台管理员业务实现类
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${jwt.admin.expire-time:7200}")
    private Long adminExpireTime;

    /**
     * 密码加密器（匹配用户注册时的加密规则）
     */
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public AdminLoginVO login(AdminLoginDTO loginDTO) {
        // 1. 按账号查询用户
        User user = userMapper.selectByAccount(loginDTO.getAccount());
        if (user == null) {
            throw new RuntimeException("账号不存在");
        }

        // 2. 校验是否为管理员角色（role=3）
        if (user.getRole() != 3) {
            throw new RuntimeException("非管理员账号，禁止登录");
        }

        // 3. 校验账号状态（是否禁用，status=0禁用，1正常）
        if (user.getStatus() == 0) {
            throw new RuntimeException("账号已被禁用，请联系超级管理员");
        }

        // 4. 校验密码（匹配BCrypt加密后的密码）
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        // 5. 生成JWT令牌
        String token = jwtUtil.generateAdminToken(user.getId(), user.getUsername(), user.getRole());

        // 6. 封装响应VO
        AdminLoginVO loginVO = new AdminLoginVO();
        loginVO.setAdminId(user.getId());
        loginVO.setUsername(user.getUsername());
        loginVO.setToken(token);
        loginVO.setExpireTime(adminExpireTime);
        loginVO.setRole(user.getRole());

        return loginVO;
    }
}