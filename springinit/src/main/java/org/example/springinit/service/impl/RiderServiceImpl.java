package org.example.springinit.service.impl;


import org.example.springinit.DTO.RiderAuditDTO;
import org.example.springinit.DTO.RiderPendingAuditDTO;
import org.example.springinit.DTO.RiderRegisterDTO;
import org.example.springinit.VO.PageResult;
import org.example.springinit.VO.RiderAuditVO;
import org.example.springinit.VO.RiderRegisterVO;
import org.example.springinit.mapper.RiderAuditLogMapper;
import org.example.springinit.mapper.RiderMapper;
import org.example.springinit.pojo.Rider;
import org.example.springinit.pojo.RiderAuditLog;
import org.example.springinit.service.RiderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 骑手业务实现类
 */
@Service
public class RiderServiceImpl implements RiderService {

    @Autowired
    private RiderMapper riderMapper;
    @Autowired
    private RiderAuditLogMapper auditLogMapper;

    // 审核结果映射
    private static final Map<Integer, String> AUDIT_RESULT_MAP = new HashMap<>();
    // 审核结果→骑手状态映射
    private static final Map<Integer, Integer> AUDIT_TO_RIDER_STATUS_MAP = new HashMap<>();
    static {
        AUDIT_RESULT_MAP.put(1, "审核通过");
        AUDIT_RESULT_MAP.put(2, "审核驳回");
        AUDIT_TO_RIDER_STATUS_MAP.put(1, 1); // 审核通过→骑手状态1（正常）
        AUDIT_TO_RIDER_STATUS_MAP.put(2, 2); // 审核驳回→骑手状态2（禁用）
    }

    /**
     * 密码加密器
     */
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RiderRegisterVO register(RiderRegisterDTO registerDTO) {
        // 1. 校验手机号是否已注册
        Rider phoneRider = riderMapper.selectByPhone(registerDTO.getPhone());
        if (phoneRider != null) {
            throw new RuntimeException("手机号已注册");
        }

        // 2. 校验身份证号是否已注册
        Rider idCardRider = riderMapper.selectByIdCard(registerDTO.getIdCard());
        if (idCardRider != null) {
            throw new RuntimeException("身份证号已注册");
        }

        // 3. DTO转实体
        Rider rider = new Rider();
        BeanUtils.copyProperties(registerDTO, rider);

        // 4. 密码加密
        rider.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        // 5. 初始化状态为0-待审核（无需手动设置，XML已默认赋值）
        // rider.setStatus(0);

        // 6. 插入数据库
        int rows = riderMapper.insert(rider);
        if (rows <= 0) {
            throw new RuntimeException("注册失败，请重试");
        }

        // 7. 封装响应VO
        RiderRegisterVO registerVO = new RiderRegisterVO();
        BeanUtils.copyProperties(rider, registerVO);


        return registerVO;
    }
    @Override
    public PageResult<Rider> getPendingAuditRiders(RiderPendingAuditDTO queryDTO) {
        // 1. 权限校验（仅管理员可查）
        if (queryDTO.getRole() != 3) {
            throw new RuntimeException("仅平台管理员可查询待审核骑手");
        }

        // 2. 查询待审核骑手总数
        Long total = riderMapper.countPendingAuditRiders();
        if (total == 0) {
            return new PageResult<>(0L, 0, null, queryDTO.getPageNum(), queryDTO.getPageSize());
        }

        // 3. 查询待审核骑手列表
        List<Rider> riderList = riderMapper.selectPendingAuditRiders(queryDTO);

        // 4. 计算总页数
        Integer pages = (int) Math.ceil((double) total / queryDTO.getPageSize());

        return new PageResult<>(total, pages, riderList, queryDTO.getPageNum(), queryDTO.getPageSize());
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RiderAuditVO auditRider(RiderAuditDTO auditDTO) {
        // 1. 校验骑手是否存在
        Rider rider = riderMapper.selectById(auditDTO.getRiderId());
        if (rider == null) {
            throw new RuntimeException("骑手不存在");
        }

        // 2. 校验骑手是否已审核（状态≠0）
        if (rider.getStatus() != 0) {
            throw new RuntimeException("该骑手已审核，无需重复操作");
        }

        // 3. 校验驳回时是否填写审核意见
        if (auditDTO.getAuditStatus() == 2 && !StringUtils.hasText(auditDTO.getAuditOpinion())) {
            throw new RuntimeException("驳回骑手时必须填写审核意见");
        }

        // 4. 更新骑手状态
        Integer newRiderStatus = AUDIT_TO_RIDER_STATUS_MAP.get(auditDTO.getAuditStatus());
        int updateRows = riderMapper.updateRiderStatus(auditDTO.getRiderId(), newRiderStatus);
        if (updateRows <= 0) {
            throw new RuntimeException("审核操作失败，请重试");
        }

        // 5. 记录审核日志
        RiderAuditLog auditLog = new RiderAuditLog();
        auditLog.setRiderId(auditDTO.getRiderId());
        auditLog.setAdminId(auditDTO.getAdminId());
        auditLog.setAuditStatus(auditDTO.getAuditStatus());
        auditLog.setAuditOpinion(auditDTO.getAuditOpinion());
        auditLogMapper.insert(auditLog);

        // 6. 封装响应VO
        RiderAuditVO auditVO = new RiderAuditVO();
        auditVO.setRiderId(rider.getId());
        auditVO.setRiderName(rider.getUsername());
        auditVO.setRiderPhone(rider.getPhone());
        auditVO.setRiderStatus(newRiderStatus);
        auditVO.setAuditResultDesc(AUDIT_RESULT_MAP.get(auditDTO.getAuditStatus()));
        auditVO.setAuditTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(auditLog.getAuditTime()));

        return auditVO;
    }

}