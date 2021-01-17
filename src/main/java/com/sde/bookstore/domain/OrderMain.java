package com.sde.bookstore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@Entity
public class OrderMain {

    @Id
    @GeneratedValue
    private Long orderMainId;

    private BigDecimal totalPrice;

    private Date createdTime;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "orderMain")
    @JsonIgnore
    private Set<OrderItem> orderItems;
}
