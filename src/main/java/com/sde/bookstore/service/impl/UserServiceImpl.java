package com.sde.bookstore.service.impl;

import com.sde.bookstore.domain.User;
import com.sde.bookstore.enums.UserRoleType;
import com.sde.bookstore.forms.UserLoginForm;
import com.sde.bookstore.forms.UserRegisterForm;
import com.sde.bookstore.repository.RoleRepository;
import com.sde.bookstore.repository.UserRepository;
import com.sde.bookstore.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userInfoRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userInfoRepository, RoleRepository roleRepository) {
        this.userInfoRepository = userInfoRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Iterable<User> findAllUsers() {
        return userInfoRepository.findAll();
    }

    @Override
    public String findRoleNameByUserId(Long userId) {
        Optional<User> user = userInfoRepository.findByUserId(userId);
        return user.get().getRole().getRoleName();
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return userInfoRepository.findByUserName(userName);
    }

    @Override
    public Optional<User> findByUserId(Long userId) {
        return userInfoRepository.findByUserId(userId);
    }

    @Override
    public Boolean checkUserLogin(UserLoginForm loginForm, User userInfo) {
        return userInfo.getUserPassword().equals(loginForm.getUserPassword());
    }

    @Override
    public Optional<User> addUser(UserRegisterForm registerForm, UserRoleType roleType) {
        User user = new User();
        BeanUtils.copyProperties(registerForm,user);
        user.setRole(roleRepository.findByRoleName(roleType.getRoleName()).get());
        return Optional.of(userInfoRepository.save(user));
    }

    @Override
    public Boolean deleteUser(Long userId) {
        userInfoRepository.deleteById(userId);
        return Boolean.TRUE;
    }

}
