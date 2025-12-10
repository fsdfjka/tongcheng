package org.example.springinit.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.example.springinit.pojo.RiderAuditLog;

@Mapper
public interface RiderAuditLogMapper {
    /**
     * 新增审核日志
     */
    int insert(RiderAuditLog auditLog);
}