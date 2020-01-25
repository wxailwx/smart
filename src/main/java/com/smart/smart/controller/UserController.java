package com.smart.smart.controller;

import com.smart.smart.model.User;
import com.smart.smart.service.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "用户相关接口")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @ApiOperation("新增用户接口")
    @PostMapping("/user")
    public User addUser(@RequestParam("name") String name,@RequestParam("number") String number,@RequestParam("college") String college,@RequestParam("password") String password){
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setCollege(college);
        user.setNumber(number);
        return userRepository.saveAndFlush(user);
    }
    @ApiOperation("验证用户")
    @GetMapping("/user")
    public User findUser(@RequestParam("number")String number){
        return userRepository.findByNumber(number);
    }
    @ApiOperation("更新用户信息")
    @PutMapping("/user")
    public User updateUser(@RequestParam("number")String number,@RequestParam("name")String name,@RequestParam("password")String password,@RequestParam("college") String college){
        User user = new User();
        user.setName(name);
        user.setNumber(number);
        user.setCollege(college);
        user.setPassword(password);
        return userRepository.saveAndFlush(user);
    }

}
