package com.sde.bookstore.service;

import com.sde.bookstore.domain.User;
import com.sde.bookstore.enums.UserRoleType;
import com.sde.bookstore.forms.UserLoginForm;
import com.sde.bookstore.forms.UserRegisterForm;

import java.util.Optional;

public interface UserService {
    Iterable<User> findAllUsers();

    String findRoleNameByUserId(Long userId);

    Optional<User> findByUserName(String userName);

    Optional<User> findByUserId(Long userId);

    Boolean checkUserLogin(UserLoginForm loginForm, User userInfo);

    Optional<User> addUser(UserRegisterForm registerForm, UserRoleType roleType);

    Boolean deleteUser(Long userId);

}
