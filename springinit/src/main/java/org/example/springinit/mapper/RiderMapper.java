package org.example.springinit.mapper;

import jakarta.validation.constraints.NotNull;
import org.example.springinit.DTO.RiderPendingAuditDTO;
import org.example.springinit.pojo.Rider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 骑手数据访问接口
 */
@Mapper
public interface RiderMapper {
    /**
     * 根据手机号查询骑手（校验唯一性）
     */
    Rider selectByPhone(@Param("phone") String phone);

    /**
     * 根据身份证号查询骑手（校验唯一性）
     */
    Rider selectByIdCard(@Param("idCard") String idCard);

    /**
     * 新增骑手（注册）
     */
    int insert(Rider rider);
    /**
     * 查询待审核骑手列表（状态=0）
     */
    List<Rider> selectPendingAuditRiders(RiderPendingAuditDTO queryDTO);

    /**
     * 查询待审核骑手总数
     */
    Long countPendingAuditRiders();

    /**
     * 更新骑手状态
     */
    int updateRiderStatus(@Param("id") Long id, @Param("status") Integer status);

    Rider selectById(@NotNull(message = "骑手ID不能为空") Long riderId);
}