package com.sde.bookstore.forms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserLoginForm {

    private String userName;

    @JsonProperty("password")
    private String userPassword;

//    private Boolean autoLogin;
//
//    private String type;
}
