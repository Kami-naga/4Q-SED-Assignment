package com.sde.bookstore.forms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserLoginForm {

    @JsonProperty("name")
    private String userName;

    @JsonProperty("pwd")
    private String userPassword;
}
