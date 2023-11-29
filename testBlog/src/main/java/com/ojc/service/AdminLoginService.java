package com.ojc.service;

import com.ojc.domain.ResponseResult;
import com.ojc.domain.entity.User;

public interface AdminLoginService {
    ResponseResult login(User user);

    /*ResponseResult logout();*/

}
