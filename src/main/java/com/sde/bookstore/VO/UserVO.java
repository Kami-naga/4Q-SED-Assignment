package com.sde.bookstore.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserVO {

    private Long userId;

    private String userName;

    private String roleName;

    private String currentAuthority;

    private String status;

    private String type;
}