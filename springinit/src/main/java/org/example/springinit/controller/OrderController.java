package org.example.springinit.controller;

import org.example.springinit.DTO.OrderCreateDTO;
import org.example.springinit.service.OrderService;
import org.example.springinit.util.JwtUtil;
import org.example.springinit.util.Result;
import org.example.springinit.VO.OrderCreateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 订单控制器
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 商户创建订单接口
     * @param createDTO 订单创建入参
     * @param request 请求对象（用于获取JWT令牌）
     * @return 订单创建结果
     */
    @PostMapping("/create")
    public Result<OrderCreateVO> createOrder(
            @Validated @RequestBody OrderCreateDTO createDTO,
            HttpServletRequest request) {
        try {
            // 1. 从请求头获取JWT令牌
            String token = request.getHeader("Authorization");
            if (token == null || !token.startsWith("Bearer ")) {
                return Result.error("未登录，请先登录");
            }
            token = token.substring(7); // 去除"Bearer "前缀

            // 2. 解析令牌获取用户ID和角色
            Long senderId = jwtUtil.getUserIdFromToken(token);
            Integer role = jwtUtil.getRoleFromToken(token);

            // 3. 校验是否为商户角色
            if (role != 2) {
                return Result.error("仅商户可创建订单");
            }

            // 4. 设置寄件人ID（商户ID）
            createDTO.setSenderId(senderId);

            // 5. 调用Service创建订单
            OrderCreateVO createVO = orderService.createOrder(createDTO);

            return Result.success("订单创建成功", createVO);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("服务器内部错误：" + e.getMessage());
        }
    }
}