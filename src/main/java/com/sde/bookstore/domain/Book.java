package com.sde.bookstore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue
    private  Long bookId;

    private  String bookName;

    private BigDecimal price;

    private Integer stock;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "book")
    @JsonIgnore
    private Set<OrderItem> orderItems;
}
