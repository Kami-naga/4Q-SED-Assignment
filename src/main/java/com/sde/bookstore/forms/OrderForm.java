package com.sde.bookstore.forms;

import com.sde.bookstore.VO.OrderItemVO;
import com.sde.bookstore.domain.OrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
public class OrderForm {

    private Long orderMainId;

    private BigDecimal totalPrice;

    private Date createdTime;

    private Long userId;

    private Set<OrderItemForm> orderItems;
}
