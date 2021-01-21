package com.sde.bookstore.forms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BookForm {

    private Long bookId;

    private String bookName;

    private BigDecimal price;

    private Integer stock;
}
