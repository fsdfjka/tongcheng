package org.example.springinit.service.impl;

import org.example.springinit.DTO.OrderCreateDTO;
import org.example.springinit.mapper.OrderMapper;
import org.example.springinit.pojo.Order;
import org.example.springinit.service.OrderService;
import org.example.springinit.VO.OrderCreateVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单业务实现类
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 生成唯一订单编号：商户ID + 时间戳（精确到毫秒）
     * @param senderId 商户ID
     * @return 订单编号
     */
    private String generateOrderNo(Long senderId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String timeStr = sdf.format(new Date());
        return senderId + "_" + timeStr;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderCreateVO createOrder(OrderCreateDTO createDTO) {
        // 1. 校验商户角色（此处假设senderId已通过JWT解析并校验为商户，若未校验可在此处查询用户角色）
        // User user = userMapper.selectById(createDTO.getSenderId());
        // if (user == null || user.getRole() != 2) {
        //     throw new RuntimeException("仅商户可创建订单");
        // }

        // 2. 转换DTO为实体类
        Order order = new Order();
        BeanUtils.copyProperties(createDTO, order);

        // 3. 生成订单编号
        String orderNo = generateOrderNo(createDTO.getSenderId());
        order.setOrderNo(orderNo);

        // 4. 初始化订单状态：0-待接单
        order.setStatus(0);

        // 5. 初始化创建时间（也可由数据库自动生成）
        order.setCreateTime(new Date());

        // 6. 插入数据库
        int rows = orderMapper.insert(order);
        if (rows <= 0) {
            throw new RuntimeException("订单创建失败");
        }

        // 7. 封装响应VO
        OrderCreateVO createVO = new OrderCreateVO();
        createVO.setOrderNo(orderNo);
        createVO.setStatus(order.getStatus());
        createVO.setAmount(order.getAmount());
        createVO.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getCreateTime()));

        return createVO;
    }
}