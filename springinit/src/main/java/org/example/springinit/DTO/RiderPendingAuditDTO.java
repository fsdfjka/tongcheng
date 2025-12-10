package org.example.springinit.DTO;

import lombok.Data;

/**
 * 待审核骑手查询DTO
 */
@Data
public class RiderPendingAuditDTO {
    /**
     * 页码（默认1）
     */
    private Integer pageNum = 1;

    /**
     * 每页条数（默认10）
     */
    private Integer pageSize = 10;

    /**
     * 审核人角色（从JWT解析，必须为3-管理员）
     */
    private Integer role;
}