package org.example.springinit.pojo;

import lombok.Data;
import java.util.Date;

/**
 * 骑手审核日志实体类
 */
@Data
public class RiderAuditLog {
    /**
     * 日志主键ID
     */
    private Long id;

    /**
     * 骑手ID
     */
    private Long riderId;

    /**
     * 审核人ID
     */
    private Long adminId;

    /**
     * 审核结果：1-通过，2-驳回
     */
    private Integer auditStatus;

    /**
     * 审核意见
     */
    private String auditOpinion;

    /**
     * 审核时间
     */
    private Date auditTime;
}