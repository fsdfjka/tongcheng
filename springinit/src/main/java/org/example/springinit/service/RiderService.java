package org.example.springinit.service;

import org.example.springinit.DTO.RiderAuditDTO;
import org.example.springinit.DTO.RiderPendingAuditDTO;
import org.example.springinit.DTO.RiderRegisterDTO;
import org.example.springinit.VO.PageResult;
import org.example.springinit.VO.RiderAuditVO;
import org.example.springinit.VO.RiderRegisterVO;
import org.example.springinit.pojo.Rider;

/**
 * 骑手业务层接口
 */
public interface RiderService {
    /**
     * 骑手注册
     * @param registerDTO 注册入参
     * @return 注册结果
     */
    RiderRegisterVO register(RiderRegisterDTO registerDTO);

    /**
     * 查询待审核骑手列表（分页）
     */
    PageResult<Rider> getPendingAuditRiders(RiderPendingAuditDTO queryDTO);

    /**
     * 审核骑手
     */
    RiderAuditVO auditRider(RiderAuditDTO auditDTO);
}