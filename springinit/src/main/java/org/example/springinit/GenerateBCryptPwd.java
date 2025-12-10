package org.example.springinit;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// 直接运行这个类的 main 方法，生成合法密文
public class GenerateBCryptPwd {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "admin123"; // 要匹配的明文密码
        String bcryptPwd = encoder.encode(rawPassword);
        // 打印结果：复制这个密文（比如 $2a$10$xxxx...）
        System.out.println("✅ 合法BCrypt密文：" + bcryptPwd);
    }
}