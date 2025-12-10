package org.example.springinit.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.example.springinit.DTO.RiderAuditDTO;
import org.example.springinit.DTO.RiderPendingAuditDTO;
import org.example.springinit.DTO.RiderRegisterDTO;
import org.example.springinit.VO.PageResult;
import org.example.springinit.VO.RiderAuditVO;
import org.example.springinit.VO.RiderRegisterVO;
import org.example.springinit.pojo.Rider;
import org.example.springinit.service.RiderService;
import org.example.springinit.util.JwtUtil;
import org.example.springinit.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 骑手控制器
 */
@RestController
@RequestMapping("/api/rider")
public class RiderController {

    @Autowired
    private RiderService riderService;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 骑手注册接口
     */
    @PostMapping("/register")
    public Result<RiderRegisterVO> register(@Validated @RequestBody RiderRegisterDTO registerDTO) {
        try {
            RiderRegisterVO registerVO = riderService.register(registerDTO);
            return Result.success("注册成功，请等待管理员审核", registerVO);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("服务器内部错误：" + e.getMessage());
        }
    }
    /**
     * 管理员查询待审核骑手列表
     */
    @GetMapping("/pending-audit")
    public Result<PageResult<Rider>> getPendingAuditRiders(
            @Validated RiderPendingAuditDTO queryDTO,
            HttpServletRequest request) {
        try {
            // 1. 解析JWT令牌，校验管理员权限
            String token = request.getHeader("Authorization");
            if (token == null || !token.startsWith("Bearer ")) {
                return Result.error("未登录，请先登录");
            }
            token = token.substring(7);
            Integer role = jwtUtil.getRoleFromToken(token);
            queryDTO.setRole(role);

            // 2. 查询待审核骑手
            PageResult<Rider> pageResult = riderService.getPendingAuditRiders(queryDTO);
            return Result.success("查询成功", pageResult);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("服务器内部错误：" + e.getMessage());
        }
    }

    /**
     * 管理员审核骑手
     */
    @PostMapping("/audit")
    public Result<RiderAuditVO> auditRider(
            @Validated @RequestBody RiderAuditDTO auditDTO,
            HttpServletRequest request) {
        try {
            // 1. 解析JWT令牌，校验管理员权限
            String token = request.getHeader("Authorization");
            if (token == null || !token.startsWith("Bearer ")) {
                return Result.error("未登录，请先登录");
            }
            token = token.substring(7);
            Integer role = jwtUtil.getRoleFromToken(token);
            if (role != 3) {
                return Result.error("仅平台管理员可审核骑手");
            }

            // 2. 设置审核人ID（管理员ID）
            Long adminId = jwtUtil.getUserIdFromToken(token);
            auditDTO.setAdminId(adminId);

            // 3. 执行审核
            RiderAuditVO auditVO = riderService.auditRider(auditDTO);
            return Result.success(AUDIT_RESULT_MAP.get(auditDTO.getAuditStatus()), auditVO);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("服务器内部错误：" + e.getMessage());
        }
    }

    // 审核结果映射常量（Controller内定义）
    private static final Map<Integer, String> AUDIT_RESULT_MAP = new HashMap<>();
    static {
        AUDIT_RESULT_MAP.put(1, "审核通过");
        AUDIT_RESULT_MAP.put(2, "审核驳回");
    }
}
