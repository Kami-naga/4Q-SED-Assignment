package com.sde.bookstore.enums;

import lombok.Getter;

@Getter
public enum UserRoleType {
    ADMINISTRATOR(0,"管理人","ADMINISTRATOR"),
    CUSTOM(1,"顧客","CUSTOM");

    private Integer code;

    private String msg;

    private String roleName;

    UserRoleType(Integer code, String msg, String roleName){
        this.code = code;
        this.msg = msg;
        this.roleName = roleName;
    }
}
