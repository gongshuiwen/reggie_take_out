package org.example.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.reggie.entity.Orders;
import org.springframework.transaction.annotation.Transactional;

public interface OrdersService extends IService<Orders> {

    @Transactional
    Boolean submitOrder(Orders order);

    Page<Orders> pageWithUserIdOrderByOrderTimeDesc(Long pageNum, Long pageSize, Long userId);
}
