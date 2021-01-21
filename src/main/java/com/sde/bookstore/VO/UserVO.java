package com.sde.bookstore.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserVO {

    private Long userId;

    @JsonProperty("name")
    private String userName;

    private String roleName;
}