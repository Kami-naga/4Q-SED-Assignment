package com.sde.bookstore.forms;

import lombok.Data;

@Data
public class OrderItemForm {

    private Long orderItemId;

    private Integer amount;

    private Long bookId;
}
