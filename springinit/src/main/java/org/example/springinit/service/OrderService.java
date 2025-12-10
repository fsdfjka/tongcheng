package org.example.springinit.service;

import org.example.springinit.DTO.OrderCreateDTO;
import org.example.springinit.VO.OrderCreateVO;

/**
 * 订单业务层接口
 */
public interface OrderService {
    /**
     * 商户创建订单
     * @param createDTO 订单创建入参
     * @return 订单创建结果
     */
    OrderCreateVO createOrder(OrderCreateDTO createDTO);
}