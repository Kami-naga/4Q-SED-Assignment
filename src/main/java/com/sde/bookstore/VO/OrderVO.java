package com.sde.bookstore.VO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class OrderVO {

    private Long orderMainId;

    private BigDecimal totalPrice;

    private Date createdTime;

    private Long userId;

    private List<OrderItemVO> orderItems;
}
