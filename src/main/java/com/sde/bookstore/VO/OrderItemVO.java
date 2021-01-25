package com.sde.bookstore.VO;

import lombok.Data;

@Data
public class OrderItemVO {

    private Integer amount;

    private Long bookId;

    private String bookName;
}
