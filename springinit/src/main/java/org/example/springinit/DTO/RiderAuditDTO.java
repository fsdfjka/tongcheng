package org.example.springinit.DTO;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 骑手审核入参DTO
 */
@Data
public class RiderAuditDTO {
    /**
     * 骑手ID（必填）
     */
    @NotNull(message = "骑手ID不能为空")
    private Long riderId;

    /**
     * 审核人ID（从JWT解析，前端无需传递）
     */
    private Long adminId;

    /**
     * 审核结果：1-通过，2-驳回（必填）
     */
    @NotNull(message = "审核结果不能为空")
    @Range(min = 1, max = 2, message = "审核结果只能是1（通过）或2（驳回）")
    private Integer auditStatus;

    /**
     * 审核意见（驳回时必填）
     */
    private String auditOpinion;
}