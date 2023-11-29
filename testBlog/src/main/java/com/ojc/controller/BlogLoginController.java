package com.ojc.controller;

import com.ojc.domain.ResponseResult;
import com.ojc.domain.entity.User;
import com.ojc.enums.AppHttpCodeEnum;
import com.ojc.exception.SystemException;
import com.ojc.service.BlogLoginService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class BlogLoginController {

    @Resource
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){

        if (!StringUtils.hasText(user.getUserName())){
            //提示必须输入用户名 ,抛出异常,封装提示信息到异常中
            //交给GlobalExceptionHandler统一处理,在统一异常处理中将异常现象转为JSON信息给前端
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }

        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }


}
