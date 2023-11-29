package com.ojc.service;

import com.ojc.domain.ResponseResult;
import com.ojc.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
