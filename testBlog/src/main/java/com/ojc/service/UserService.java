package com.ojc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ojc.domain.ResponseResult;
import com.ojc.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-05-12 00:45:49
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);
}

