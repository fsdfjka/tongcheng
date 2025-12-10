package org.example.springinit.VO;

import lombok.Data;

/**
 * 骑手审核响应VO
 */
@Data
public class RiderAuditVO {
    /**
     * 骑手ID
     */
    private Long riderId;

    /**
     * 骑手姓名
     */
    private String riderName;

    /**
     * 骑手手机号
     */
    private String riderPhone;

    /**
     * 审核后状态：1-正常，2-禁用
     */
    private Integer riderStatus;

    /**
     * 审核结果描述：审核通过/审核驳回
     */
    private String auditResultDesc;

    /**
     * 审核时间（格式：yyyy-MM-dd HH:mm:ss）
     */
    private String auditTime;
}