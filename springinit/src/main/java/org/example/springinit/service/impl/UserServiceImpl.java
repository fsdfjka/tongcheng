package org.example.springinit.service.impl;

import org.example.springinit.DTO.UserLoginDTO;
import org.example.springinit.DTO.UserRegisterDTO;
import org.example.springinit.mapper.UserMapper;
import org.example.springinit.pojo.User;
import org.example.springinit.VO.UserLoginVO;
import org.example.springinit.service.UserService;

import org.example.springinit.util.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户业务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 密码加密器
     */
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User register(UserRegisterDTO registerDTO) {
        // 1. 校验手机号是否已注册
        User existUser = userMapper.selectByPhone(registerDTO.getPhone());
        if (existUser != null) {
            throw new RuntimeException("手机号已注册");
        }

        // 2. 转换DTO为实体类
        User user = new User();
        BeanUtils.copyProperties(registerDTO, user);

        // 3. 密码加密
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        // 4. 转换角色为Integer（DTO中为String，避免前端传参类型问题）
        user.setRole(Integer.parseInt(registerDTO.getRole()));

        // 5. 插入数据库
        userMapper.insert(user);

        return user;
    }
    // 新增登录方法
    @Override
    public UserLoginVO login(UserLoginDTO loginDTO) {
        // 1. 根据手机号查询用户
        User user = userMapper.selectByPhone(loginDTO.getPhone());
        System.out.println(user);
        if (user == null) {
            throw new RuntimeException("手机号未注册");
        }

        // 2. 校验账号状态（是否禁用）
        if (user.getStatus() == 0) {
            throw new RuntimeException("账号已被禁用，请联系平台管理员");
        }

        // 3. 校验密码（BCrypt匹配明文与密文）
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        // 4. 生成JWT令牌
        String token = jwtUtil.generateToken(user.getId(), user.getRole());

        // 5. 封装响应数据
        UserLoginVO loginVO = new UserLoginVO();
        BeanUtils.copyProperties(user, loginVO);
        loginVO.setToken(token);
        loginVO.setExpireTime(jwtUtil.getExpireTime());

        return loginVO;
    }
}