package com.sde.bookstore.controller;

import com.sde.bookstore.VO.UserVO;
import com.sde.bookstore.domain.User;
import com.sde.bookstore.enums.UserRoleType;
import com.sde.bookstore.forms.UserLoginForm;
import com.sde.bookstore.forms.UserRegisterForm;
import com.sde.bookstore.service.RoleService;
import com.sde.bookstore.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService){
        this.userService = userService;
        this.roleService = roleService;
    }

    @ApiOperation(value="get all users")
    @GetMapping("/")
    public ResponseEntity<List<UserVO>> getAllUsers(HttpServletRequest request){
        User userInfo = (User) request.getSession().getAttribute("User");
        if(userInfo == null || !UserRoleType.ADMINISTRATOR.getRoleName().equals(userInfo.getRole().getRoleName())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<UserVO> userVOList = new ArrayList<>();
        userService.findAllUsers().forEach(user -> {
            UserVO userInfoVO = new UserVO();
            BeanUtils.copyProperties(user, userInfoVO);
            userInfoVO.setRoleName(userService.findRoleNameByUserId(user.getUserId()));
            userVOList.add(userInfoVO);
        });
        return new ResponseEntity<>(userVOList, HttpStatus.OK);
    }

    @ApiOperation(value="login",notes="simply add login status into session")
    @PostMapping("/login")
    public ResponseEntity<UserVO> login(@RequestBody UserLoginForm loginForm,
                                            HttpServletRequest request){
        return userService.findByUserName(loginForm.getUserName()).map(userInfo -> {
            if(userService.checkUserLogin(loginForm,userInfo)){
                request.getSession().setAttribute("User", userInfo);
                UserVO userInfoVO = new UserVO();
                BeanUtils.copyProperties(userInfo, userInfoVO);
                userInfoVO.setRoleName(userService.findRoleNameByUserId(userInfo.getUserId()));
                return new ResponseEntity<>(userInfoVO,HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity<UserVO>(HttpStatus.UNAUTHORIZED);
            }
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value="log out")
    @PostMapping("/logout")
    public ResponseEntity<UserVO> logout(HttpServletRequest request){
        request.getSession().invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value="register")
    @PostMapping("/custom")
    public ResponseEntity<UserVO> customRegister(@RequestBody UserRegisterForm registerForm){
        return userService.addUser(registerForm ,UserRoleType.CUSTOM).map(userInfo -> {
            UserVO userInfoVO = new UserVO();
            BeanUtils.copyProperties(userInfo, userInfoVO);
            userInfoVO.setRoleName(roleService.findByRoleName(UserRoleType.CUSTOM.getRoleName()).get().getRoleName());
            return new ResponseEntity<>(userInfoVO,HttpStatus.CREATED);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @ApiOperation(value="add new administrator")
    @PostMapping("/admin")
    public ResponseEntity<UserVO> adminRegister(@RequestBody UserRegisterForm registerForm,
                                                    HttpServletRequest request){
        User userInfo = (User) request.getSession().getAttribute("User");
        if(userInfo == null || !UserRoleType.ADMINISTRATOR.getRoleName().equals(userInfo.getRole().getRoleName())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return userService.addUser(registerForm,UserRoleType.ADMINISTRATOR).map(user -> {
            UserVO userInfoVO = new UserVO();
            BeanUtils.copyProperties(user, userInfoVO);
            userInfoVO.setRoleName(roleService.findByRoleName(UserRoleType.ADMINISTRATOR.getRoleName()).get().getRoleName());
            return new ResponseEntity<>(userInfoVO,HttpStatus.CREATED);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.CONFLICT));
    }
}
