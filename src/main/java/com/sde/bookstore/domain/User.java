package com.sde.bookstore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@ToString(exclude = {"orders"})
public class User {

    @Id
    @GeneratedValue
    private Long userId;

    private String userName;

    private String userPassword;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "roleId")
    @JsonIgnore
    private Role role;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    @JsonIgnore
    private Set<OrderMain> orders;
}
