package com.sde.bookstore.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Role {

    @Id
    @GeneratedValue
    private Long roleId;

    private String roleName;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "role")
    @JsonIgnore
    private Set<User> users;
}
