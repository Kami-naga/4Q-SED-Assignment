package com.sde.bookstore.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookVO {

    private Long bookId;

    @JsonProperty("name")
    private String bookName;

    private BigDecimal price;

    private Integer stock;

}
