package org.example.springinit.mapper;

import org.example.springinit.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 订单数据访问接口
 */
@Mapper
public interface OrderMapper {
    /**
     * 新增订单（商户创建订单）
     * @param order 订单实体
     * @return 受影响行数
     */
    int insert(Order order);

    /**
     * 根据订单编号查询订单
     * @param orderNo 订单编号
     * @return 订单实体
     */
    Order selectByOrderNo(@Param("orderNo") String orderNo);
}